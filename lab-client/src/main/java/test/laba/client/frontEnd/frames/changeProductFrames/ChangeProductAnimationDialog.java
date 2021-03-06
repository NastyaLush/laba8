package test.laba.client.frontEnd.frames.changeProductFrames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import test.laba.client.frontEnd.frames.HomeFrame;
import test.laba.client.frontEnd.frames.IFunctionString;
import test.laba.client.util.Command;
import test.laba.client.util.Constants;
import test.laba.common.dataClasses.Product;
import test.laba.common.dataClasses.UnitOfMeasure;
import test.laba.common.exception.VariableException;
import test.laba.common.responses.Response;


import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class ChangeProductAnimationDialog extends ChangeProductDialog {
    private static final int WIDTH_STANDARD_TEXT_FIELD = 15;
    private static final int HEIGHT_STANDARD_AREA = 5;
    private static final int HEIGHT_STANDARD_BUTTON = 24;
    private static final int HEIGHT_STANDARD_AREA_SMALL = 5;
    private final Product product;
    private final Long key;
    private JButton remove;
    private final HashMap<String, IFunctionString> functions;
    private final Dimension standardDimension = new Dimension(23, 24);

    public ChangeProductAnimationDialog(ResourceBundle resourceBundle, Product product, Long key, HomeFrame homeFrame) {
        super(resourceBundle, homeFrame);
        this.product = product;
        functions = new HashMap<>();
        functions.put(localisation(Constants.PRODUCT_NAME), product::getName);
        functions.put(localisation(Constants.COORDINATE_X), product.getCoordinates()::getX);
        functions.put(localisation(Constants.COORDINATE_Y), product.getCoordinates()::getY);
        functions.put(localisation(Constants.PRICE), product::getPrice);
        functions.put(localisation(Constants.MANUFACTURE_COST), product::getManufactureCost);
        functions.put(localisation(Constants.UNIT_OF_MEASURE), product::getUnitOfMeasure);
        if (product.getOwner() != null) {
            functions.put(localisation(Constants.PERSON_NAME), product.getOwner()::getName);
            functions.put(localisation(Constants.BIRTHDAY), product.getOwner()::getBirthday);
            functions.put(localisation(Constants.HEIGHT), product.getOwner()::getHeight);
            functions.put(localisation(Constants.LOCATION_NAME), product.getOwner().getLocation()::getName);
            functions.put(localisation(Constants.LOCATION_X), product.getOwner().getLocation()::getX);
            functions.put(localisation(Constants.LOCATION_Y), product.getOwner().getLocation()::getY);
        } else {
            functions.put(localisation(Constants.PERSON_NAME), null);
            functions.put(localisation(Constants.BIRTHDAY), null);
            functions.put(localisation(Constants.HEIGHT), null);
            functions.put(localisation(Constants.LOCATION_NAME), null);
            functions.put(localisation(Constants.LOCATION_X), null);
            functions.put(localisation(Constants.LOCATION_Y), null);
        }

        this.key = key;
    }


    @Override
    protected JTextField createButtonGroup(String name, String description, boolean saveToDelete) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(standardDimension);

        JTextField textField = new JTextField(getDescription(name));
        textField.setPreferredSize(new Dimension(WIDTH_STANDARD_TEXT_FIELD, HEIGHT_STANDARD_BUTTON));
        Component enter = Box.createRigidArea(new Dimension(0, HEIGHT_STANDARD_AREA));


        addLabels(saveToDelete, label, textField, enter);
        return textField;
    }

    @Override
    protected void addKey() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    protected void addActionButtons() {
        super.addActionButtons();
        remove = new JButton(localisation(Constants.REMOVE_KEY));
        remove.setBackground(Color.GRAY);
        remove.setFont(getLabelFont());
        remove.addActionListener(e -> {
            try {
                addRemoveListener();
            } catch (VariableException ex) {
                getHomeFrame().show(ex.getMessage());
            }
        });
        getMainPlusPanel().add(Box.createRigidArea(new Dimension(0, HEIGHT_STANDARD_AREA_SMALL)));
        getMainPlusPanel().add(remove);
    }

    protected Response createResponse() {
        Response response = new Response(Command.REMOVE_KEY.getString());
        response.setKeyOrID(key);
        return response;
    }

    protected abstract void addRemoveListener() throws VariableException;

    @Override
    protected void removeActionButtons() {
        super.removeActionButtons();
        getMainPlusPanel().remove(remove);

    }

    protected Response createUpdateResponse() throws VariableException {
        Response response = new Response(Command.UPDATE_ID.getString());
        Product newProduct = addProduct();
        response.setProduct(newProduct);
        response.setFlagUdateID(true);
        response.setKeyOrID(product.getId());
        return response;
    }


    @Override
    protected JMenu unitOfMeas(String name, String description) {
        JLabel label = new JLabel(name + "(" + description + ")");
        label.setForeground(Color.gray);
        label.setFont(getLabelFont());
        label.setPreferredSize(standardDimension);


        JMenu menu = createUMMenu(localisation(Objects.requireNonNull(localUM(product.getUnitOfMeasure()))));
        menu.setName(getDescription(name));
        JMenuBar menuBar = unitOfMeasureButton(menu);

        getMainPlusPanel().add(label);
        getMainPlusPanel().add(menuBar);
        getMainPlusPanel().add(Box.createRigidArea(new Dimension(0, HEIGHT_STANDARD_AREA)));
        return menu;
    }

    protected String getDescription(String name) {
        if (functions.get(name) != null) {
            return String.valueOf(functions.get(name).getText());
        } else {
            return "";
        }
    }

    private Constants localUM(UnitOfMeasure unitOfMeasure) {
        switch (unitOfMeasure) {
            case PCS:
                return Constants.PCS;
            case MILLILITERS:
                return Constants.MILLILITERS;
            case GRAMS:
                return Constants.GRAMS;
            default:
                return null;

        }
    }

}
