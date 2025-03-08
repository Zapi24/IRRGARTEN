# frozen_string_literal: true

module Irrgarten

  class Dice

    @@MAX_USES=5 #Numero max de usos de armas y escudos
    @@MAX_INTELLIGENCE=10 #Valor max de inteligencia en jugadores y monstruos
    @@MAX_STRENGTH=10 #Valor max de fuerza en jugadores y monstruos
    @@RESURRECT_PROB=0.3 #Prob de que un jugador sea resucitado en cada turno
    @@WEAPONS_REWARD=2 #num max de armas recibidas al ganar un combate
    @@SHIELDS_REWARD=3 #num max de escudos recibidos al ganar un combate
    @@HEALTH_REWARD=5 #num max de unidades de salud recibidas al ganar un combate
    @@MAX_ATTACK=3 #max potencia de las armas
    @@MAX_SHIELD=2 #max potencia de los escudos
    @generador=Random.new


    def self.random_pos(max)

      @generador.rand(0..max-1)#Devuelve un entero entre 0 y max-1
    end

    def self.who_starts(n_players)

      @generador.rand(0..n_players-1)
    end

    def self.random_intelligence

      @generador.rand*@@MAX_INTELLIGENCE
    end

    def self.random_strength

      @generador.rand*@@MAX_STRENGTH
    end

    def self.resurrect_player

      if(@generador.rand <= @@RESURRECT_PROB)

        return true
      else

        return false
      end
    end

    def self.weapons_reward

      @generador.rand(0..@@WEAPONS_REWARD-1)
    end

    def self.shields_reward

      @generador.rand(0..@@SHIELDS_REWARD-1)
    end

    def self.health_reward

      @generador.rand(0..@@HEALTH_REWARD-1)
    end

    def self.weapon_power

      @generador.rand*@@MAX_ATTACK
    end

    def self.shield_power

      @generador.rand*@@MAX_SHIELD
    end

    def self.uses_left

      @generador.rand(0..@@MAX_USES-1)
    end

    def self.intensity(competence)

      @generador.rand*competence
    end

    def self.discard_element(uses_left)

      valor_inver=uses_left.to_f/@@MAX_USES #Importante el .to_f para que se tome una division de flotantes

      if(valor_inver < @generador.rand || uses_left == 0)

        true
      else

        false
      end
    end

    def self.next_step(preference, valid_moves, intelligence)

      if @generador.rand < intelligence #Devolver la preferencia si se cumple la condicion de inteligencia

        preference #return
      else
        indice_aleatorio = @generador.rand(0..valid_moves.size-1)

        valid_moves[indice_aleatorio] #return
      end
    end









  end

end
