# frozen_string_literal: true

module Irrgarten
  class Game

    attr_reader :laberinto, :log

    @@MAX_ROUNDS=10
    @@TAM_LABERINTO=12
    @@NUM_MONSTERS=10

      def initialize(n_players,debug)#MODO DEBUG para comprobaciones y trabajos sencillos

        @monstruos=Array.new
        @jugadores=Array.new

        @current_player_index = Dice.who_starts(n_players)
        @log = ''
        @n_monsters=0 #Variable añadida para contar los monstruos dentro del juego

        #Añadir la salida y creamos el laberinto
        random_exit=Dice.random_pos(@@TAM_LABERINTO)

        @laberinto = Labyrinth.new_labyrinth(@@TAM_LABERINTO,@@TAM_LABERINTO,@@TAM_LABERINTO-1,random_exit) #Deben estar en el lateral inferio

        if (!debug) #Configuracion normal
          for i in 0...n_players

            @jugadores.push(Player.new_player(i, Dice.random_strength, Dice.random_intelligence))
          end
          configure_labyrinth
        else #CONFIGURACION EN MODO DEBUG, PRUEBA

          for i in 0...n_players

            @jugadores.push(Player.new_player(i, 4, 4))
          end
          @laberinto.add_block(Orientations::HORIZONTAL, 2, 0, 10)

          monstruo=Monster.new_monster("Miniom debil",5,5)
          @monstruos.push(monstruo)
          @laberinto.add_monster(1,1,monstruo)

          monstruo=Monster.new_monster("Miniom fuerte ",15,15)
          @monstruos.push(monstruo)
          @laberinto.add_monster(1,2,monstruo)

          @laberinto.spread_players(@jugadores,false) #No establecemos una disposicion de jugadores aleatoria
        end
      end

      def self.new_game(n_players,debug)

        new(n_players,debug)

      end

    def finished

      @laberinto.have_a_winner
    end

    def next_step(prefered_direction)
      @log=""
      current_player=@jugadores[@current_player_index]

      if(!current_player.dead)
        #Comprueba que la direccion, es realmente la direccion actual
        if(actual_direction(prefered_direction) != prefered_direction)

          log_player_no_orders
        end

        monster=@laberinto.put_player(actual_direction(prefered_direction),current_player)

        if (monster == nil ) #Al moverse no habia monstruo

          log_no_monster
        else #Si habia monstruo, empieza el combate

          manage_reward(combat(monster)) #Se establece recompensa al terminar el combate
        end

      else

        manage_resurrection #Si esta muerto que se tire el dado, para intentar revivir
      end

      end_game = finished
      if(!end_game) #Comprueba que no haya terminado al ganar un jugador

        next_player
      end

      end_game
    end

    def get_game_state
      players=""
      monsters=""
      for i in 0...@jugadores.size

        players="#{players}#{@jugadores[i].to_s}\n"
      end

      for i in 0...@monstruos.size

        monsters="#{monsters}#{@monstruos[i].to_s}\n"
      end

      Game_state.new_game_state(@laberinto.to_s,players,monsters,@current_player_index,finished,@log)
    end

    def configure_labyrinth

      #Introducimos los bloques de forma custom
      @laberinto.add_block(Orientations::HORIZONTAL, 2, 0, 10)
      @laberinto.add_block(Orientations::HORIZONTAL,5,0,5)
      @laberinto.add_block(Orientations::HORIZONTAL,5,7,5)
      @laberinto.add_block(Orientations::HORIZONTAL, 7, 4, 4)
      @laberinto.add_block(Orientations::VERTICAL, 8, 1, 3)
      @laberinto.add_block(Orientations::VERTICAL, 8, 10, 3)
      @laberinto.add_block(Orientations::HORIZONTAL, 9, 2, 3)
      @laberinto.add_block(Orientations::HORIZONTAL, 9,7,3)

      #Introducimos los monstruos

      while (@n_monsters < @@NUM_MONSTERS)

        randomRow=Dice.random_pos(@@TAM_LABERINTO)
        randomCol=Dice.random_pos(@@TAM_LABERINTO)
        if (laberinto.empty_pos(randomRow,randomCol))

          monstruo=Monster.new_monster("Miniom",10,10)
          @monstruos.push(monstruo)
          @laberinto.add_monster(randomRow,randomCol,monstruo)
          @n_monsters=@n_monsters+1
        end
      end
      #Introducimos los jugadrores
      @laberinto.spread_players(@jugadores,false)
    end

    def next_player
      @current_player_index=@current_player_index+1
      if(@current_player_index>=@jugadores.size)

        @current_player_index=0
      end
    end

    def  actual_direction(prefered_direction)

      #movimientos_validos.Array.new
      currentRow=@jugadores[@current_player_index].row
      currenCol=@jugadores[@current_player_index].col

      movimientos_validos = @laberinto.valid_moves(currentRow,currenCol)

      @jugadores[@current_player_index].move(prefered_direction,movimientos_validos)


    end

    def combat(monster)

      current_player=@jugadores[@current_player_index]
      rounds=0
      winner= GameCharacter::PLAYER

      player_attack = current_player.attack() #Calculamos la fuerza de ataque del jugador
      lose = monster.defend(player_attack) #Vemos si el monstruo aguanta el golpe

      while !lose && rounds < @@MAX_ROUNDS #Mientras ninguno de los dos pierda, la batalla continua

        monster_attack=monster.attack()
        lose = current_player.defend(monster_attack)
        winner = GameCharacter::MONSTER
        rounds=rounds+1

        if !lose #Ataca de vuelta el jugador

          player_attack = current_player.attack()
          lose = monster.defend(player_attack)
          winner = GameCharacter::PLAYER
        end
      end

      log_rounds(rounds,@@MAX_ROUNDS)
      winner
    end

    def manage_reward(winner)
      if(winner == GameCharacter::PLAYER)

        current_player=@jugadores[@current_player_index]
        current_player.receive_reward
        log_player_won
      else

        log_player_skip_turn
      end

    end

    #Metodo nuevo que remplaza la posicion actual del jugador por la de un fuzzy_player
    def replace_player_with_fuzzy(player, fuzzy_player)

      index = @jugadores.find_index(player) #me devuelve el indice del jugador
      @jugadores.delete_at(index) #Elimina el jugador
      @jugadores.insert(index,fuzzy_player)
    end

    def manage_resurrection
      if Dice.resurrect_player

        current_player=@jugadores[@current_player_index]
        fuzzy_player = FuzzyPlayer.new(current_player)
        current_player.resurrect
        replace_player_with_fuzzy(current_player,fuzzy_player) #Sustituye player por fuzzy_player
        log_resurrected
      else

        log_player_skip_turn
      end

    end

    def log_player_won
      @log += "El jugador ha ganado el combate\n"
    end

    def log_monster_won
      @log += "Ha ganado el combate el monstruo \n"
    end

    def log_resurrected
      @log += "El jugador ha sido resucitado y se ha convertido en un fuzzy_player"+"\n"
    end

    def log_player_skip_turn
      @log += "El jugador ha perdido el turno por estar muerto. \n";
    end

    def log_player_no_orders
      @log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible).\n"
    end

    def log_no_monster
      @log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse.\n"
    end

    def log_rounds(rounds, max)
      @log += "Se han producido #{rounds} de #{max} rondas de combate.\n"
    end

  end
end



