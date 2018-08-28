/**

 Loki is an easy to use library that transforms simple annotations into a powerful GUI
 Copyright (C) 2018 Ricardo Simoes

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published
 by the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package ch.hephaistos.utilities.loki.util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

/**
 * Describes how the label will be displayed
 *
 * <b>ChangeLod 15.11.2017 by RDMS</b>
 *
 * Added new function for the insertion of a single Node
 * Changed the standard addNode function to accept 2 Nodes
 *
 * @author I-Al-Istannen, : https://github.com/I-Al-Istannen
 *
 */
public enum LabelDisplayOrder {
    /**
     * The labels are displayed on the left of the field.
     *
     * <br>
     * <pre>
     *  | Label | Field |
     *  | Label | Field |
     * </pre>
     */
    SIDE_BY_SIDE(2) {
        @Override
        public InsertionPosition addNode(InsertionPosition position, Node label, Node node,
                GridPane pane) {
            pane.add(label, position.column, position.row);
            pane.add(node, position.column + 1, position.row);
            return new InsertionPosition(position.row + 1, position.column);
        }

        @Override
        public InsertionPosition addNode(InsertionPosition position, Node node, GridPane pane) {
            pane.add(node, position.column, position.row);
            return new InsertionPosition(position.row + 1, position.column);
        }

        @Override
        public InsertionPosition addSeparator(InsertionPosition position, GridPane pane){
            pane.add(new Separator(), position.column, position.row);
            pane.add(new Separator(), position.column + 1, position.row);
            return new InsertionPosition(position.row + 1, position.column);
        }

    },
    /**
     * The labels are displayed above the field.
     *
     * <br>
     * <pre>
     *  | Label |
     *  | Field |
     *  | Label |
     *  | Field |
     * </pre>
     */
    ABOVE_FIELD(1) {
        @Override
        public InsertionPosition addNode(InsertionPosition position, Node label, Node node,
                GridPane pane) {
            pane.add(label, position.column, position.row);
            pane.add(node, position.column, position.row + 1);
            return new InsertionPosition(position.row + 2, position.column);
        }

        @Override
        public InsertionPosition addNode(InsertionPosition position, Node node, GridPane pane) {
            pane.add(node, position.column, position.row);
            return new InsertionPosition(position.row + 1, position.column);
        }

        @Override
        public InsertionPosition addSeparator(InsertionPosition position, GridPane pane){
            pane.add(new Separator(), position.column, position.row);
            return new InsertionPosition(position.row + 1, position.column);
        }
    };

    private int columns;

    LabelDisplayOrder(int columns) {
        this.columns = columns;
    }

    public int getColumnCount() {
        return columns;
    }

    /**
     * Adds a node with a label to a {@link GridPane}.
     *
     * @param position The position to add them at
     * @param label The {@link Label} to use
     * @param node The {@link Node} to add
     * @param pane The Pane to add it to
     * @return The new row and the new column
     */
    public abstract InsertionPosition addNode(InsertionPosition position, Node label, Node node,
            GridPane pane);

    /**
     * Adds a node to a {@link GridPane}.
     *
     * This node is supposed to be alone in one row, but might wary depending on implementation.
     *
     * @param position The position to add them at
     * @param node The {@link Node} to add
     * @param pane The Pane to add it to
     * @return The new row and the new column
     *
     */
    public abstract InsertionPosition addNode(InsertionPosition position, Node node, GridPane pane);

    /**
     * This function is used to input a JavaFX Separator in the next position.
     * @param position The position at which the separator will be inserted
     * @param pane the GridPane which is to receive the separator
     * @return InsertionPosition for the next Element
     */
    public abstract InsertionPosition addSeparator(InsertionPosition position, GridPane pane);

    public static class InsertionPosition {

        public final int row;
        public final int column;

        public InsertionPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

}
