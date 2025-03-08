# frozen_string_literal: true
module Irrgarten
  class CombatElement

    attr_reader :effect, :uses
    attr_writer :effect, :uses

    def initialize(effect,uses)

      @effect = effect
      @uses = uses
    end

    def self.new_combat_element(effect,uses)

      new(effect,uses)
    end

    def produce_effect
      if (@uses > 0)

        @uses=@uses-1 #return
      else

        0 #return
      end
    end

    def discard

      Dice.discard_element(@uses)
    end

    def to_s

      "[#{@effect},#{@uses}]"
    end





  end #de clase
end #de modulo
