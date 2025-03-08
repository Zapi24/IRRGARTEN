# frozen_string_literal: true

module Irrgarten

  require_relative 'combat_element'
  class Weapon < CombatElement

    def initialize(power,uses)

      super(power,uses)
    end

    def self.new_weapon(power,uses) #Constructor

      new(power,uses)
    end

    def attack

      produce_effect
    end

    def to_s

      "W #{super.to_s}"
    end
  end

end
