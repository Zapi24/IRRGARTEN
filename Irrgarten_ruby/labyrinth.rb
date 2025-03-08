# frozen_string_literal: true

module Irrgarten
  class Labyrinth
    attr_reader :nRows, :nCols

    @@BLOCK_CHAR = 'X'
    @@EMPTY_CHAR = '-'
    @@MONSTER_CHAR = 'M'
    @@COMBAT_CHAR = 'C'
    @@EXIT_CHAR = 'E'
    @@ROW = 0
    @@COL= 1

    def initialize(nRows,nCols,exitRow,exitCol)

      @nRows=nRows
      @nCols=nCols
      @exitRow=exitRow
      @exitCol=exitCol

      @celdas_monstruo = Array.new(nRows) { Array.new(nCols) }
      @celdas_jugador = Array.new(nRows) { Array.new(nCols) }
      @celdas_juego = Array.new(nRows) { Array.new(nCols) }

      for i in 0...nRows
        for j in 0...nCols
          @celdas_monstruo[i][j] = nil
          @celdas_jugador[i][j] = nil
          @celdas_juego[i][j] = @@EMPTY_CHAR
        end
      end

      @celdas_juego[exitRow][exitCol]=@@EXIT_CHAR
    end

    def self.new_labyrinth(nRows,nCols,exitRow,exitCol)

      new(nRows,nCols,exitRow,exitCol)

    end

    def aniadir_obstaculo(i,j)
      if(@celdas_juego[i][j]==@@EMPTY_CHAR && pos_ok(i,j))

        @celdas_juego[i][j]=@@BLOCK_CHAR
      end
    end

    # Random Bool para establecer si queremos randomizar la pos de los players
    def spread_players(players,random)

      contador=0
      pos = Array.new(2)
      players.each do |p| #p hace referencia a cada elemento p dentro de players

        if (random)

          pos = random_empty_pos
        else

          pos[@@ROW]=0; #Los colocaremos dentro de la primera fila
          pos[@@COL]=contador; #Iran cogiendo las posiciones (0,0) (0,1) (0,2) (0,3) sucesivamente
          contador=contador+1;
        end

        put_player2D(-1, -1, pos[@@ROW], pos[@@COL], p) # Devuelve el monstruo a donde sea
      end
    end


    def have_a_winner

      @celdas_jugador[@exitRow][@exitCol]!=nil
    end

    def to_s

      lista="Tamanio:(#{@nRows}x#{@nCols}) -Salida:(#{@exitRow},#{@exitCol})\n"
      lista="#{lista}Celdas de juego: \n"
      for i in 0...@nRows
        for j in 0...@nCols

          lista="#{lista}#{@celdas_juego[i][j]} "
        end
        lista="#{lista}\n"
      end

      #NO ES NECESARIO
      # lista="#{lista}Celdas de monstruos: \n"
      #       for i in 0...@nRows
      #         for j in 0...@nCols
      #
      #           lista="#{lista}#{@celdas_monstruo[i][j]} "
      #         end
      #         lista="#{lista}\n"
      #       end
      #
      #       lista="#{lista}Celdas de jugadores: \n"
      #       for i in 0...@nRows
      #         for j in 0...@nCols
      #
      #           lista="#{lista}#{@celdas_jugador[i][j]} "
      #         end
      #         lista="#{lista}\n"
      #       end

      lista
    end

    def add_monster(row,col,monster)
      if(pos_ok(row,col))

        monster.set_pos(row,col)

        @celdas_juego[row][col]=@@MONSTER_CHAR
        @celdas_monstruo[row][col]=monster
      end
    end

    def put_player(direction,player)
      old_row=player.row
      old_col=player.col

      new_pos=dir_2pos(old_row,old_col,direction)

      put_player2D(old_row,old_col,new_pos[@@ROW],new_pos[@@COL],player)
    end

    def add_block(orientation,start_row,start_col,length)

      if( orientation == Orientations::VERTICAL) #Bloque vertical

        inc_row=1
        inc_col=0
      else #Bloque horizontal

        inc_row=0
        inc_col=1
      end

      row=start_row
      col=start_col

      while (pos_ok(row,col) && empty_pos(row,col) && length>0)

        aniadir_obstaculo(row,col)

        length=length-1
        row=row+inc_row
        col=col+inc_col
      end
    end

    def valid_moves(row, col)
      output = Array.new

      if can_step_on(row + 1, col)
        output.push(Directions::DOWN)
      end

      if can_step_on(row - 1, col)
        output.push(Directions::UP)
      end

      if can_step_on(row, col + 1)
        output.push(Directions::RIGHT)
      end

      if can_step_on(row, col - 1)
        output.push(Directions::LEFT)
      end

      output
    end


    def pos_ok(row,col)

      row>=0 && row<@nRows && col>=0 && col<@nCols
    end

    def empty_pos(row,col)
      if(pos_ok(row,col))

        @celdas_juego[row][col]==@@EMPTY_CHAR
      else
        false
      end

    end

    def monster_pos(row,col)
      if(pos_ok(row,col))

        @celdas_monstruo[row][col]!=nil
      else
        false
      end
    end

    def exit_pos(row,col)

      row==@exitRow && col==@exitCol
    end

    def combat_pos(row,col)
      if(pos_ok(row,col))

        @celdas_juego[row][col]==@@COMBAT_CHAR && monster_pos(row,col)
      else
        false
      end
    end

    def can_step_on(row,col)

      pos_ok(row,col) && (empty_pos(row,col) || monster_pos(row,col) || exit_pos(row,col))
    end

    def update_old_pos(row,col)
      if(pos_ok(row,col))
        if(@celdas_juego[row][col] == @@COMBAT_CHAR)

          @celdas_juego[row][col]=@@MONSTER_CHAR
        else

          @celdas_juego[row][col]=@@EMPTY_CHAR
        end
      end
    end

    def dir_2pos(row,col,direction)
      posicion = Array.new(2)

      case direction #Equivalente a switch

      when Directions::DOWN
        posicion[0]=row+1
        posicion[1]=col
      when Directions::LEFT
        posicion[0]=row
        posicion[1]=col-1
      when Directions::RIGHT
        posicion[0]=row
        posicion[1]=col+1
      when Directions::UP
        posicion[0]=row-1
        posicion[1]=col
      end

      posicion
    end

    def random_empty_pos
      posicion = Array.new(2)

      begin

        rowAux=Dice.random_pos(@nRows)
        colAux=Dice.random_pos(@nCols)


      end while(@celdas_juego[rowAux][colAux] != @@EMPTY_CHAR) #Equivalente a un do while en ruby


      posicion[@@ROW]=rowAux
      posicion[@@COL]=colAux

      posicion
    end

    def put_player2D(old_row, old_col, row, col, player)
      output = nil

      if(can_step_on(row,col))
        if(pos_ok(row,col))

          p = @celdas_jugador[old_row][old_col]

          if( p == player)

            update_old_pos(old_row,old_col)
            @celdas_jugador[old_row][old_col]=nil
          end
        end

        if(monster_pos(row,col)) #Hay un monstruo

          @celdas_juego[row][col]=@@COMBAT_CHAR
          output=@celdas_monstruo[row][col]
        else #Es una casilla vacia o la celda de salida
          number=player.numero
          @celdas_juego[row][col]=number
        end

        @celdas_jugador[row][col]=player
        player.set_pos(row,col)
      end

      output
    end
  end
end



