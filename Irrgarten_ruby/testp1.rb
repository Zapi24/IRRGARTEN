# frozen_string_literal: true

module Irrgarten

  require_relative 'irrgarten'
  require_relative 'shield.rb'
  require_relative 'weapon.rb'
  require_relative 'dice.rb'
  require_relative 'game_state'
  require_relative 'monster'
  require_relative 'player'
  require_relative 'labyrinth'
  require_relative 'game'
  require_relative 'controller'
  require_relative 'text_ui'
  d


  class Testp1 #EQUIVALENTE A  MAIN

    vista = TextUI.new
    juego = Game.new_game(1,true) #True para modo debug activado
    controlador = Controller.new(juego,vista)

    controlador.play


    # #PRUEBAS DE DIRECTIONS
    #
    #     puts Directions::RIGHT
    #     puts Directions::LEFT
    #     puts
    #     #PRUEBAS DE ESCUDO
    #
    #     escudo= Shield.new(3.0,4)
    #
    #     puts escudo.to_s
    #     escudo.protect
    #     puts escudo.to_s
    #     puts
    #     #PRUEBAS DE WEAPON
    #
    #     arma= Weapon.new(2.0,4)
    #
    #     puts arma.to_s
    #     arma.attack
    #     puts arma.to_s
    #     puts
    #     #PRUEBAS DE DICE
    #
    #     puts Dice.random_pos(5)
    #
    #     #PRUEBAS DE GAME STATE
    #     juego= Game_state.new("laberinto","jugadores","monstruos",0,true,"logg")
    #
    #     puts juego.winner
    #     puts juego.monsters
    #     puts juego.labyrinthv
    #     puts
    #
    #     #PRUEBAS DE MONSTER
    #
    #     monstruo=Monster.new_monster("MonstruoEj",10,10)
    #
    #     puts monstruo.dead
    #     puts monstruo.attack
    #
    #     monstruo.set_pos(2,2)
    #     puts monstruo.to_s
    #     puts monstruo.got_wounded
    #     puts
    #
    #     #PRUEBAS DE PLAYER
    #
    #     jugador=Player.new_player(0,10,10)
    #
    #     puts jugador.dead
    #     jugador.set_pos(2,3)
    #     puts jugador.to_s
    #
    #     arma=jugador.new_weapon
    #     escudo=jugador.new_weapon
    #     puts arma.to_s
    #     puts escudo.to_s
    #     puts
    #
    #     #PRUEBAS DE LABYRINTH
    #     laberinto=Labyrinth.new_labyrinth(5,7,4,4)
    #     puts laberinto.have_a_winner
    #     puts laberinto.to_s
    #
    #     laberinto.add_monster(2,3,monstruo)
    #     puts laberinto.to_s
    #
    #     puts laberinto.pos_ok(9,0)
    #     puts laberinto.empty_pos(2,2)
    #     puts laberinto.monster_pos(2,2)
    #     puts laberinto.exit_pos(4,4)
    #
    #     #posicon=Array.new(2)
    #     posicion=laberinto.dir_2pos(3,3,Directions::LEFT)
    #     puts "(#{posicion[0]},#{posicion[1]})"
    #
    #     posicion=laberinto.random_empty_pos
    #     puts "(#{posicion[0]},#{posicion[1]})"
    #     puts
    #
    #     #PRUEBAS DE CLASE GAME
    #     juego = Game.new_game(5,true)
    #     puts juego.finished
    #     puts juego.laberinto.to_s
    #
    #     juego.log_monster_won
    #     juego.log_player_won
    #     juego.next_player
    #     juego.log_resurrected
    #
    #     juego_actual = juego.get_game_state
    #
    #     puts juego_actual.players
    #     puts juego_actual.monsters
    #     puts juego_actual.log
    #     puts juego_actual.current_player


  end
end



