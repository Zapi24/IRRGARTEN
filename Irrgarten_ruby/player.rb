# frozen_string_literal: true

module Irrgarten
  class Player < LabyrinthCharacter

    attr_reader :numero

    @@INITIAL_HEALTH=10
    @@MAX_WEAPONS=2
    @@MAX_SHIELDS=3
    @@HITS2LOSE=3

    def initialize(numero , intelligence , strength )

      super("Player##{numero}",intelligence,strength,@@INITIAL_HEALTH)

      @numero = numero
      @consecutive_hits = 0 #Valor nulo inicial
      @armas = Array.new   # Array para almacenar armas
      @escudos = Array.new # Array para almacenar escudos
    end

    def self.new_player(numero,strengh,intelligence)

      new(numero,strengh,intelligence)
    end

    def resurrect

      @armas.clear
      @escudos.clear
      @health=@@INITIAL_HEALTH #super.health = @@INITAL_HEALTH !!!!!
      @consecutive_hits=0
    end

    def move(direction,valid_moves)

      size=valid_moves.size
      contained=valid_moves.include?(direction) #Equivalente a contains dentro de java

      if size > 0 && !contained
        #Si hay movimientos disponibles, pero no esta el que buscamos, nos movemos hacia el primer movimiento de la lista
        valid_moves[0] #return
      else

        direction #return
      end
    end

    def attack

      @strength+sum_weapons #super.strengt+sum_weapons
    end

    def defend(received_attack)

      manage_hit(received_attack)
    end

    def receive_reward

      wReward = Dice.weapons_reward
      sReward = Dice.shield_power

      for i in 0...wReward

        wnew = new_weapon
        receive_weapon(wnew)
      end

      for i in 0...sReward

        snew = new_shield
        receive_shield(snew)
      end

      extra_health=Dice.health_reward

      @health=@health+extra_health #super.health
    end

    def to_s

      string="#{super.to_s}"

      if !@armas.empty? #Comprueba que no este vacio

        string=string+"\n     Armas:"
        for i in 0...@armas.size

          string=string+"#{@armas[i]}"
        end
      end

      if (!@escudos.empty?) #Comprueba que no este vacio

        string=string+"\n     Escudos:"
        for i in 0...@escudos.size

          string=string+"#{@escudos[i]}"
        end
      end
      string
    end

    def receive_weapon(w)

      @armas.reject! { |weapon| weapon.discard } # Elimina las armas que cumplen la condición discard

      if @armas.size < @@MAX_WEAPONS

        @armas.push(w)
      end

    end
    def receive_shield(s)

      @escudos.reject! { |shield| shield.discard} # Elimina los escudos que cumplen la condición discard

      if @escudos.size < @@MAX_SHIELDS

        @escudos.push(s)
      end
    end

    def new_weapon

      Weapon.new(Dice.weapon_power,Dice.uses_left)
    end

    def new_shield

      Shield.new(Dice.shield_power,Dice.uses_left)
    end

    def sum_weapons
      suma_armas=0

      for i in 0...@armas.size

        suma_armas=suma_armas+@armas[i].attack
      end

      suma_armas
    end

    def sum_shields
      suma_escudos=0

      for i in 0...@escudos.size

        suma_escudos=suma_escudos+@escudos[i].protect
      end

      suma_escudos
    end

    def defensive_energy

      @intelligence+sum_shields
    end

    def manage_hit(received_attack)
      if defensive_energy < received_attack

        got_wounded # pierde uno de vida
        inc_consecutive_hits # aumenta el numero de golpes consecutivos que recibe
      else

        reset_hits #Si se defiende,se resetean los golpes consecutivos
      end

      #Si esta muerto, o tiene el numero de golpes consecutivos maximos, pierde
      if @consecutive_hits == @@HITS2LOSE || dead

        reset_hits
        true #Returneamos true
      else

        false #Returneamos false
      end
    end

    def reset_hits

      @consecutive_hits=0
    end

    def inc_consecutive_hits

      @consecutive_hits=@consecutive_hits+1
    end




  end
end

