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

import javafx.beans.NamedArg;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This Class is used to create a generic SpinnerValueFactor for usage with {@link javafx.scene.control.Spinner}.
 * It converts every Number internally into a BigDecimal using the conversion trough .doubleValue().
 * It is not built with the intention of keeping as much precision as possible - it is merely used to give users a simple
 * way of visualizing any kind of Numeric Variable.
 * @author Ricardo DAniel Monteiro Simoes
 */
public class NumberSpinnerValueFactory extends SpinnerValueFactory<BigDecimal> {

    private Type type = BigDecimal.class;
    private BigDecimal currentNumber;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal amountToStepBy;

    /**
     * Constructs a new BigDecimalSpinnerValueFactory that sets the initial
     * value to be equal to the min value, and a default {@code amountToStepBy}
     * of one.
     *
     * @param min The minimum allowed double value for the Spinner.
     * @param max The maximum allowed double value for the Spinner.
     * @param type the Type of a variable
     */
    public NumberSpinnerValueFactory(@NamedArg("min") Number min,
            @NamedArg("max") Number max, Type type) {
        this(min, max, min, type);
    }


    public NumberSpinnerValueFactory(@NamedArg("min") Number min,
            @NamedArg("max") Number max,
            @NamedArg("initialValue") Number initialValue,
            Type type) {
        this(min, max, initialValue, 1, type);
    }


    public NumberSpinnerValueFactory(@NamedArg("min") Number min,
            @NamedArg("max") Number max,
            @NamedArg("initialValue") Number initialValue,
            @NamedArg("amountToStepBy") Number amountToStepBy,
            Type type) {
        this.type = type;
        setMin(turnIntoBD(min));
        setMax(turnIntoBD(max));
        setAmountToStepBy(turnIntoBD(amountToStepBy));
        super.setConverter((StringConverter<BigDecimal>) new BigDecimalStringConverter());

        valueProperty().addListener((o, oldValue, newValue) -> {
            if (isSmallerOrEqualThan(newValue, getMin())) {
                setNumber(getMin());
            } else if (isLargerOrEqualThan(newValue, getMax())) {
                setNumber(getMax());
            }
        });
        setNumber((isLargerOrEqualThan(initialValue, getMin())
                && isSmallerOrEqualThan(initialValue, getMax()))
                ? turnIntoBD(initialValue) : getMin());
    }


    private BigDecimal turnIntoBD(Number number) {
        return BigDecimal.valueOf(number.doubleValue());
    }

    /**
     * The following functions are used for cleaner comparison of {@link BigDecimal} variables.
     */

    private boolean isSmallerOrEqualThan(BigDecimal isSmaller, BigDecimal than) {
        return (isSmaller.compareTo(than) <= 0);
    }

    private boolean isSmallerOrEqualThan(Object isSmaller, Object than) {
        return (((BigDecimal) isSmaller).compareTo((BigDecimal) than) <= 0);
    }

    private boolean isLargerOrEqualThan(BigDecimal isLarger, BigDecimal than) {
        return (isLarger.compareTo(than) >= 0);
    }

    private boolean isLargerOrEqualThan(Object isLarger, Object than) {
        return (((BigDecimal) isLarger).compareTo((BigDecimal) than) >= 0);
    }

    /**
     * Comparisonfunctions terminated
     */

    /**
     * {@inheritDoc}
     */
    public void setAmountToStepBy(BigDecimal amountToStepBy) {
        this.amountToStepBy = amountToStepBy;
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal getAmountToStepBy() {
        return amountToStepBy;
    }

    /**
     * {@inheritDoc}
     */
    private final void setMin(BigDecimal min) {
        this.min = min;
    }

    /**
     * {@inheritDoc}
     */
    public final BigDecimal getMin() {
        return min;
    }

    /**
     * {@inheritDoc}
     */
    private final void setMax(BigDecimal max) {
        this.max = max;
    }

    /**
     * {@inheritDoc}
     */
    public final BigDecimal getMax() {
        return max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrement(int steps) {
        final BigDecimal min = getMin();
        final BigDecimal max = getMax();
        final BigDecimal newIndex
                = (getValue()).subtract(turnIntoBD(steps)
                        .multiply(getAmountToStepBy()));

        setNumber(isLargerOrEqualThan(newIndex, getMin())
                ? newIndex : (isWrapAround()
                        ? wrapValue(newIndex, min, max).add(BigDecimal.ONE) : getMin()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(int steps) {
        final BigDecimal min = getMin();
        final BigDecimal max = getMax();
        final BigDecimal currentValue = (BigDecimal) getValue();
        final BigDecimal newIndex
                = ((BigDecimal) getValue()).add(turnIntoBD(steps)
                        .multiply(getAmountToStepBy()));
        setNumber((isSmallerOrEqualThan(newIndex, getMax())
                ? newIndex : (isWrapAround()
                        ? wrapValue(newIndex, min, max).add(BigDecimal.ONE) : getMax())));

    }

    private BigDecimal wrapValue(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (max.doubleValue() == 0) {
            throw new RuntimeException();
        }
        if (value.compareTo(min) < 0) {
            return max;
        } else if (value.compareTo(max) > 0) {
            return min;
        }
        return value;
    }

    private void setNumber(BigDecimal number) {
        if (type == int.class || type == long.class) {
            setValue(number.setScale(0, RoundingMode.DOWN));
            return;
        }
        setValue(number);
    }

}
