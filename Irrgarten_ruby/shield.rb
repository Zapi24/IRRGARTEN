# frozen_string_literal: true

module Irrgarten
  require_relative 'combat_element'
  class Shield < CombatElement

    def initialize(protection,uses)

      super(protection,uses)
    end

    def self.new_shield(protection,uses) #Constructor

      new(protection,uses)
    end

    def protect

      produce_effect
      #     No puedo utilziar super, ya que al utilizarlo, me busca el metodo de mismo nombre #
      #     en la clase padre, es decir, busca defen dentro de comat_element
    end

    def to_s

      "S #{super.to_s}"
    end
  end

end

