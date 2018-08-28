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

package ch.hephaistos.utilities.loki.util.annotations;

import java.lang.annotation.*;

/**
 * This annotation is supposed to be used in conjunction with ReflectorGrid.
 * It has different variables which are used to set different options
 * during the creation of the grid. 
 * 
 * the enum <b>Fieldtype</b> is used to define if the Field is constructed
 * using a <b>TextField</b> or <b>TextArea</b>.
 * 
 * <b>editable</b> is used to define if a variable is read only or can 
 * also be written to.
 * 
 * <b>options</b> is used, once filled, to create a comboBox for the variable.
 * This is, in terms of hierarchy, above the <b>fieldtype</b> enum. As soon as
 * you fill something into options, the field for the corresponding variable
 * turns into a ComboBox.
 * 
 * @author Ricardo Daniel Monteiro Simoes
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TransferGrid {

    
    /**
     * This defines the possible options for the
     * TextInputField.
     */
    enum Fieldtype{
        TEXT_FIELD, TEXT_AREA
    }

    /**
     * This defines if a variable is write and read or read only.
     * default is set to true.
     * true == write/read
     * false == read only
     * @return if it is editable or not
     */
    public boolean editable () default true;

    /**
     * This defines if the variable is supposed to be set using a ComboBox.
     * This is above <b>fieldtype</b>. If you give options any input, it will
     * turn into a ComboBox, no matter the setting in <b>fieldtype</b>
     * 
     * To set this variable, please make use of a normal String array.
     * <b>Example:</b>
     * TransferGrid(options = {"full", "half", "none"});
     * 
     * @return Stringarray of all options.
     */
    public String[] options () default {};
    
    
    /**
     * This defines what kind of TextInputField you want.
     * Default value is set to <b>TEXTFIELD</b>.
     * You can manually change it to <b>TEXTAREA</b> for something like
     * "notes" in your object.
     * 
     * <b>WARNING</b> If options() has been filled, whatever you set here
     * gets ignored.
     * 
     * @return  the fieldType of a field
     */
    public Fieldtype fieldtype () default Fieldtype.TEXT_FIELD;


    /**
     * This gives the Label of the variable a tooltip, which the user can show when hovering
     * over it with the mouse. This function is usable no mater what fields you are using.
     *
     * @return Returns an empty String if there is no tooltip defined.
     */
    public String tooltip () default "";

}
