# frozen_string_literal: true

module Irrgarten

  require_relative 'labyrinth_character'
  class Monster < LabyrinthCharacter

    @@INITIAL_HEALTH=5

    def initialize(name,intelligence,strength)

      super(name,intelligence,strength,@@INITIAL_HEALTH)
    end

    def self.new_monster(name,intelligence,strength) #Constructor

      new(name, strength, intelligence)

    end

    def attack

      Dice.intensity(@strength)
    end

    def defend(received_attack)
      is_dead=dead #dead es un metodo de labyrinth_character

      if !is_dead
        defensive_energy = Dice.intensity(@intelligence) #super.intelligence

        if  defensive_energy < received_attack

          got_wounded #Le quitamos uno de vida al monstruo
          is_dead=dead #Comprueba de vuelta si ya esta muerto
        end
      end

      is_dead
    end

  end
end
