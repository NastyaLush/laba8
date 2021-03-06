package test.laba.client.util;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;
import test.laba.common.exception.VariableException;
import test.laba.common.dataClasses.UnitOfMeasure;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * pars variables from string to right vision
 */
public final class VariableParsing {
    private static final int FALSE_X = -233;

    private VariableParsing() {
    }

    /**
     * pars string name to right field name (not null)
     *
     * @param oldName name for parsing
     * @return string with right name
     * @throws VariableException throws when parsing is impossible
     */
    public static String toRightName(String field, String oldName, ResourceBundle resourceBundle) throws VariableException {
        String name = oldName.trim();
        if (name.isEmpty()) {
            throw new VariableException(field, localisation(resourceBundle, Constants.CAN_NOT_BE_NULL));
        }
        return name;
    }

    /**
     * pars string x to coordination x ( not null and more than -233)
     *
     * @param x number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightX(String name, String x, ResourceBundle resourceBundle) throws VariableException {
        int rightX;
        try {
            NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
            rightX = Integer.parseInt(String.valueOf(numberFormatter.parse(x)));
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_INT));
        }
        if (rightX <= FALSE_X) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_BIGGER) + " -233");
        }
        return rightX;
    }

    /**
     * pars string y to coordination y ( not null)
     *
     * @param y number for parsing
     * @return float
     * @throws VariableException throws when parsing is impossible
     */
    public static Float toRightY(String name, String y, ResourceBundle resourceBundle) throws VariableException {
        float rightY;
        NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
        try {
            rightY = Float.parseFloat(String.valueOf(numberFormatter.parse(y)));
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_FLOAT_NUMBER));
        }
        return rightY;
    }

    /**
     * pars string price to product price, the number must be integer(type long) and more than zero
     *
     * @param price number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public static Long toRightPrice(String name, String price, ResourceBundle resourceBundle) throws VariableException {
        long rightPrice;
        try {
            NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
            rightPrice = Long.parseLong(String.valueOf(numberFormatter.parse(price)));
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_LONG));
        }
        if (rightPrice <= 0) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_BIGGER) + " 0");
        }
        return rightPrice;
    }

    /**
     * pars string number to int number
     *
     * @param number number for parsing
     * @return integer
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightNumberInt(String name, String number, ResourceBundle resourceBundle) throws VariableException {
        int manufactureCost;
        try {
            NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
            manufactureCost = Integer.parseInt(String.valueOf(numberFormatter.parse(number)));
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_INT));

        }
        return manufactureCost;
    }

    /**
     * pars string number to integer long number
     *
     * @param number number for parsing
     * @return long
     * @throws VariableException throws when parsing is impossible
     */
    public static Long toRightNumberLong(String name, String number, ResourceBundle resourceBundle) throws VariableException {
        try {
            NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
            return Long.valueOf(String.valueOf(numberFormatter.parse(number)));
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_LONG));

        }
    }

    /**
     * pars string argument to unitOfMeasure type of UnitOfMeasure
     *
     * @param unit unit for parsing to unit of measure
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public static UnitOfMeasure toRightUnitOfMeasure(String name, String unit, ResourceBundle resourceBundle) throws VariableException {
        try {
            if (unit != null) {
                return UnitOfMeasure.valueOf(unit.toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.WRONG_UNIT_OF_MEASURE));
        }
        throw new VariableException(name, localisation(resourceBundle, Constants.WRONG_UNIT_OF_MEASURE));

    }

    /**
     * pars string height to integer height, which must be more zero
     *
     * @param height number for parsing
     * @return UnitOfMeasure
     * @throws VariableException throws when parsing is impossible
     */
    public static Integer toRightHeight(String name, String height, ResourceBundle resourceBundle) throws VariableException {
        Integer rightHeight = null;
        if (height != null) {
            try {
                NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
                rightHeight = Integer.valueOf(String.valueOf(numberFormatter.parse(height)));
            } catch (NumberFormatException | ParseException e) {
                throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_INT));
            }
            if (rightHeight <= 0) {
                throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_BIGGER) + " 0");
            }
        }
        return rightHeight;
    }

    /**
     * pars string birthday to birthday type of ZonedDAteTime with pattern "dd-MM-yyyy"
     *
     * @param birthday string for parsing to ZonedDateTime
     * @return ZonedDateTime
     * @throws VariableException throws when parsing is impossible
     */
    public static ZonedDateTime toRightBirthday(String name, String birthday, ResourceBundle resourceBundle) throws VariableException {
        ZonedDateTime birth = null;
        try {
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, resourceBundle.getLocale());
                Date date = dateFormat.parse(birthday);
            System.out.println(date.toString());
                Instant instant = date.toInstant();
                birth = instant.atZone(ZoneId.systemDefault());
        } catch (DateTimeException | ParseException e) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate parsed = LocalDate.parse(birthday, formatter);
                birth = ZonedDateTime.of(parsed, LocalTime.MIDNIGHT, ZoneId.of("Europe/Berlin"));
            } catch (DateTimeException dateTimeException) {
               birth = parsDate(birthday, name, resourceBundle);
            }
            System.out.println(birth);
            return birth;
        }
        return birth;
    }
    private static ZonedDateTime parsDate(String birthday, String name, ResourceBundle resourceBundle) throws VariableException {
        ZonedDateTime birth = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime parsed = LocalDateTime.parse(birthday, formatter);
            birth = ZonedDateTime.of(parsed, ZoneId.of(ZoneId.systemDefault().toString()));
        } catch (DateTimeException exception) {
            System.out.println(exception.getMessage());
            throw new VariableException(name, localisation(resourceBundle, Constants.WRONG_DATE));
        }
        return birth;
    }

    /**
     * pars string to long number
     *
     * @param number number for parsing
     * @return long number
     */
    public static Long toLongNumber(String name, String number, ResourceBundle resourceBundle) throws VariableException {
        try {
            if (number != null) {
                NumberFormat numberFormatter = NumberFormat.getInstance(resourceBundle.getLocale());
                return Long.valueOf(String.valueOf(numberFormatter.parse(number.trim())));
            } else {
                throw new VariableException(name, localisation(resourceBundle, Constants.CAN_NOT_BE_NULL));
            }
        } catch (NumberFormatException | ParseException e) {
            throw new VariableException(name, localisation(resourceBundle, Constants.MUST_BE_INTEGER_NUMBER_LONG));
        }
    }

    private static String localisation(ResourceBundle resourceBundle, Constants constants) {
        return resourceBundle.getString(constants.getString());
    }

}



