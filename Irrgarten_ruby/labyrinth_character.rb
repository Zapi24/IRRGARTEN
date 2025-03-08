# frozen_string_literal: true

module Irrgarten
  class LabyrinthCharacter

    attr_reader :row, :col, :intelligence, :strength, :health
    attr_writer :health

    def initialize(name,intelligence,strength,health)

      @name = name
      @intelligence = intelligence
      @strength = strength
      @health = health
      @row
      @col
    end

    def initialize_copy(other)

      new(other.name,other.intelligence,other.strength,other.health)
    end

    def dead

      @health < 0
    end

    def set_pos(row,col)

      @row = row
      @col = col
    end

    def to_s

      "Pos:(#{@col},#{@row}) -Nombre: #{@name} -Vida: #{@health} -Fuerza: #{@strength} -Inteligencia: #{@intelligence}"
    end

    def got_wounded

      @health=@health-1
    end
  end
end



