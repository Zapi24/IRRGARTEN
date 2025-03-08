# frozen_string_literal: true

require 'io/console'


module Irrgarten

  class TextUI

    #https://gist.github.com/acook/4190379
    def read_char
      STDIN.echo = false
      STDIN.raw!

      input = STDIN.getc.chr
      if input == "\e"
        input << STDIN.read_nonblock(3) rescue nil
        input << STDIN.read_nonblock(2) rescue nil
      end
    ensure
      STDIN.echo = true
      STDIN.cooked!

      return input
    end

    def next_move
      print "Where? "
      got_input = false
      while (!got_input)
        c = gets.chomp
        case c
        when "w"
          puts "UP ARROW"
          output = Irrgarten::Directions::UP
          got_input = true
        when "s"
          puts "DOWN ARROW"
          output = Irrgarten::Directions::DOWN
          got_input = true
        when "d"
          puts "RIGHT ARROW"
          output = Irrgarten::Directions::RIGHT
          got_input = true
        when "a"
          puts "LEFT ARROW"
          output = Irrgarten::Directions::LEFT
          got_input = true
        when "\u0003"
          puts "CONTROL-C"
          got_input = true
          exit(1)
        else
          #Error
        end
      end
      output
    end

      def show_game(game_state)
        puts "MOSTRANDO JUGADORES \n\n"
        puts "#{game_state.players}\n\n"

        puts "MONSTRANDO MONSTRUOS \n\n"
        puts "#{game_state.monsters}\n\n"

        puts "************************* \n"
        puts "* IMPRIMIENDO LABERINTO * \n"
        puts "************************* \n\n"
        puts "#{game_state.labyrinth.to_s}"

        puts "JUGADOR ACTUAL: #{game_state.current_player}\n"
        puts "LOG DEL JUEGO: \n\n"
        puts "#{game_state.log}\n"

        winner = game_state.winner

        if !winner
          puts "\nAun no hay ningun ganador, el juego continua\n"
        else
          puts <<~WINNER_MESSAGE
      ╔═══════════════════════════════════════════════╗
      ║                                               ║
      ║    ¡¡¡FELICIDADES, VALIENTE!!!                ║
      ║    Te has alzado victorioso en esta batalla   ║
      ║    épica.                                     ║
      ║                                               ║
      ║    🏆 ¡Has ganado el juego y tu nombre        ║
      ║    resonará a lo largo de de la historia! 🏆  ║
      ║                                               ║
      ║    ¡Hoy, el reino celebra tu gloriosa         ║
      ║    victoria! 🎉                               ║
      ║                                               ║
      ╚═══════════════════════════════════════════════╝
      EL JUGADOR #{game_state.current_player} HA GANADO EL JUEGO
    WINNER_MESSAGE
        end
      end

  end # class

end # module
