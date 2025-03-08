# frozen_string_literal: true


module Irrgarten
  class Game_state

    attr_writer :labyrinth, :players,:monsters,:current_player,:winner,:log #Equivale a los sets
    attr_reader :labyrinth, :players,:monsters,:current_player,:winner,:log #Equivale a los gets

    def initialize(la,p,m,c,w,lo)

      @labyrinth=la
      @players=p
      @monsters=m
      @current_player=c
      @winner=w
      @log=lo
    end

    def self.new_game_state(la,p,m,c,w,lo)

      new(la,p,m,c,w,lo)
    end

  end
end


