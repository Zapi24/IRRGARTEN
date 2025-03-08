# frozen_string_literal: true

module Irrgarten

  class FuzzyPlayer < Player

    def initialize(p)#Constructor de copia

      super(p.numero, p.intelligence, p.strength)
    end

    def move(direction, valid_moves)

      Dice.next_step(direction,valid_moves,@intelligence) #super.intelligence
    end

    def attack

      Dice.intensity(@strength+sum_weapons) #super.strength y super.sum_weapons
    end

    def defense_energy

      Dice.intensity(@intelligence+sum_shields) #super.intelligence y super.sum_shields
    end

    def to_s

      "Fuzzy #{super.to_s}"
    end
  end
end

