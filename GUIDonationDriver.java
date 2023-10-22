
/*****************************************************************************
/* Author:      Filip Segota
/* Class:       CSC 321, Spring 2021
/* Assignment:  Donations, GUI
/* File:        Donation GUI implementation
/*****************************************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUIDonationDriver extends JFrame {

    Donor[] donors;
    int count;
    boolean nameSearch;
    private boolean firstTotal, firstShow;

    // components for the GUI
    private JButton addDonor, addDonation;
    private JButton getTotal, getStatus;
    private JButton listDonations, listAllDonations;
    private JPanel buttonsPanel, inputPanel, listPanel;

    private JButton add;
    private JButton selectName, selectID, show;
    private JRadioButton r1, r2, r3, p1, p2;
    private JPanel input, group, profitPanel, information;
    private JLabel name, email, profit, id;
    private JLabel value, type, description, date;
    private JTextField nameField, emailField, idField;
    private JTextField valueField, typeField, descriptionField, dateField;
    private ButtonGroup g, profitGroup;

    private JLabel listTitle;

    private JDialog dialog;
    private JLabel message, totalLabel, showLabel;

    // Listeners
    private AddingDonor newDonor;
    private AddDonor addDonorListener;
    private AddingDonation newDonation;
    private AddDonation addDonationListener;
    private GetTotal getTotalListener;
    private ShowTotal showTotalListener;
    private SelectName nameSelected;
    private SelectID idSelected;
    private GetStatus getStatusListener;
    private ShowStatus showStatus;
    private ListDonations listDonationsListener;
    private ListAllDonations listAllDonationsListener;
    private RadioChange radioChangeListener;

    // classes for listeners
    public class RadioChange implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (r3.isSelected()) {
                p1.setEnabled(true);
                p2.setEnabled(true);
            } else {
                p1.setEnabled(false);
                p2.setEnabled(false);
                p2.setSelected(true);
            }
        }
    }

    public class ShowStatus implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            boolean found;
            int index;

            found = false;
            index = 0;

            for (int i = 0; i < count; i++) {
                if (nameSearch == true) {
                    if (donors[i].getName().compareTo(nameField.getText()) == 0) {
                        found = true;
                        index = i;
                    }
                } else if (nameSearch == false) {

                    if (Integer.toString(i).compareTo(idField.getText()) == 0) {
                        found = true;
                        index = i;
                    }
                }
            }

            if (found) {

                if (!firstShow) {
                    inputPanel.remove(showLabel);
                }
                showLabel = new JLabel();
                showLabel.setText("Active:" + donors[index].getActiveState());
                inputPanel.add(showLabel, BorderLayout.CENTER);
                inputPanel.revalidate();
                inputPanel.repaint();
                firstShow = false;
            } else {
                dialog = new JDialog();
                message = new JLabel("Can't find the specified donor!");
                dialog.add(message, BorderLayout.CENTER);
                dialog.setSize(300, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }

    public class ShowTotal implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            boolean found;
            int index, total;
            Donation[] donations;

            found = false;
            index = 0;
            total = 0;
            donations = new Donation[1000];

            for (int i = 0; i < count; i++) {
                if (nameSearch == true) {
                    if (donors[i].getName().compareTo(nameField.getText()) == 0) {
                        found = true;
                        index = i;
                    }
                } else if (nameSearch == false) {

                    if (Integer.toString(i).compareTo(idField.getText()) == 0) {
                        found = true;
                        index = i;
                    }
                }
            }

            if (found) {
                donations = donors[index].getDonations();
                for (int i = 0; i < donors[index].getIndex(); i++) {
                    total += donations[i].getValue();
                }
                if (!firstTotal) {
                    inputPanel.remove(totalLabel);
                }
                totalLabel = new JLabel();
                totalLabel.setText("Total:" + Integer.toString(total));
                inputPanel.add(totalLabel, BorderLayout.CENTER);
                inputPanel.revalidate();
                inputPanel.repaint();
                firstTotal = false;
            } else {
                dialog = new JDialog();
                message = new JLabel("Can't find the specified donor!");
                dialog.add(message, BorderLayout.CENTER);
                dialog.setSize(300, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }

    public class AddDonation implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Donation newDonation;
            Donor donor;
            boolean found;
            int index;

            found = false;
            donor = null;
            index = 0;

            for (int i = 0; i < count; i++) {
                if (nameSearch == true) {
                    if (donors[i].getName().compareTo(nameField.getText()) == 0) {
                        donor = donors[i];
                        found = true;
                        index = i;
                    }
                } else if (nameSearch == false) {

                    if (Integer.toString(i).compareTo(idField.getText()) == 0) {
                        donor = donors[i];
                        found = true;
                        index = i;
                    }
                }
            }

            if (found) {
                newDonation = new Donation(Integer.valueOf(valueField.getText()),
                        typeField.getText(), donor,
                        LocalDate.parse(dateField.getText()),
                        descriptionField.getText());
                donors[index].addDonation(newDonation);

                dialog = new JDialog();
                message = new JLabel("Donation is added successful!");
                dialog.add(message, BorderLayout.CENTER);
                dialog.setSize(300, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } else {
                dialog = new JDialog();
                message = new JLabel("Can't find the specified donor!");
                dialog.add(message, BorderLayout.CENTER);
                dialog.setSize(300, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
    }

    public class AddDonor implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Donor newDonor;
            String type;
            boolean isBusiness, forProfit;

            type = "";
            isBusiness = false;
            forProfit = false;

            if (r1.isSelected()) {
                type = r1.getText();
                isBusiness = false;
            } else if (r2.isSelected()) {
                type = r2.getText();
                isBusiness = false;
            } else if (r3.isSelected()) {
                type = r3.getText();
                isBusiness = true;
                if (p1.isSelected()) {
                    forProfit = true;
                } else {
                    forProfit = false;
                }
            }

            newDonor = new Donor(type, true, nameField.getText(),
                    emailField.getText(), isBusiness, forProfit);
            donors[count++] = newDonor;

            dialog = new JDialog();
            message = new JLabel("Donor is added successfuly!");
            dialog.add(message, BorderLayout.CENTER);
            dialog.setSize(300, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public class ListAllDonations implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
        }
    }

    public class ListDonations implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            inputPanel.setLayout(new GridLayout(3, 2));

            selectName = new JButton("Select by Name");
            selectID = new JButton("Select by ID");
            group = new JPanel();
            group.setLayout(new GridLayout(1, 2));
            group.add(selectName);
            group.add(selectID);
            inputPanel.add(group);
            nameSelected = new SelectName();
            selectName.addActionListener(nameSelected);
            idSelected = new SelectID();
            selectID.addActionListener(idSelected);

            input = new JPanel();
            input.setLayout(new GridLayout(1, 2));
            name = new JLabel("Name:");
            id = new JLabel("ID:");
            nameField = new JTextField();
            idField = new JTextField();
            input.add(name);
            input.add(nameField);
            input.add(id);
            input.add(idField);
            inputPanel.add(input);
            idField.setEnabled(false);

            show = new JButton("Show donations");
            inputPanel.add(show);
        }
    }

    public class GetStatus implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            inputPanel.setLayout(new GridLayout(4, 2));

            selectName = new JButton("Select by Name");
            selectID = new JButton("Select by ID");
            group = new JPanel();
            group.setLayout(new GridLayout(1, 2));
            group.add(selectName);
            group.add(selectID);
            inputPanel.add(group);
            nameSelected = new SelectName();
            selectName.addActionListener(nameSelected);
            idSelected = new SelectID();
            selectID.addActionListener(idSelected);

            input = new JPanel();
            input.setLayout(new GridLayout(1, 2));
            name = new JLabel("Name:");
            id = new JLabel("ID:");
            nameField = new JTextField();
            idField = new JTextField();
            input.add(name);
            input.add(nameField);
            input.add(id);
            input.add(idField);
            inputPanel.add(input);
            idField.setEnabled(false);

            show = new JButton("Show status");
            inputPanel.add(show);

            showStatus = new ShowStatus();
            show.addActionListener(showStatus);
        }
    }

    public class GetTotal implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            inputPanel.setLayout(new GridLayout(4, 2));

            selectName = new JButton("Select by Name");
            selectID = new JButton("Select by ID");
            group = new JPanel();
            group.setLayout(new GridLayout(1, 2));
            group.add(selectName);
            group.add(selectID);
            inputPanel.add(group);
            nameSelected = new SelectName();
            selectName.addActionListener(nameSelected);
            idSelected = new SelectID();
            selectID.addActionListener(idSelected);

            input = new JPanel();
            input.setLayout(new GridLayout(1, 2));
            name = new JLabel("Name:");
            id = new JLabel("ID:");
            nameField = new JTextField();
            idField = new JTextField();
            input.add(name);
            input.add(nameField);
            input.add(id);
            input.add(idField);
            inputPanel.add(input);
            idField.setEnabled(false);

            show = new JButton("Show total");
            inputPanel.add(show);

            showTotalListener = new ShowTotal();
            show.addActionListener(showTotalListener);
        }
    }

    public class SelectName implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            nameField.setEnabled(true);
            idField.setEnabled(false);
            nameSearch = true;
        }
    }

    public class SelectID implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            idField.setEnabled(true);
            nameField.setEnabled(false);
            nameSearch = false;
        }
    }

    public class AddingDonation implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            inputPanel.setLayout(new GridLayout(4, 2));

            selectName = new JButton("Select by Name");
            selectID = new JButton("Select by ID");
            group = new JPanel();
            group.setLayout(new GridLayout(1, 2));
            group.add(selectName);
            group.add(selectID);
            inputPanel.add(group);
            nameSelected = new SelectName();
            selectName.addActionListener(nameSelected);
            idSelected = new SelectID();
            selectID.addActionListener(idSelected);

            input = new JPanel();
            input.setLayout(new GridLayout(1, 2));
            name = new JLabel("Name:");
            id = new JLabel("ID:");
            nameField = new JTextField();
            idField = new JTextField();
            input.add(name);
            input.add(nameField);
            input.add(id);
            input.add(idField);
            inputPanel.add(input);
            idField.setEnabled(false);

            information = new JPanel();
            information.setLayout(new GridLayout(4, 2));
            value = new JLabel("Value:");
            type = new JLabel("Type:");
            description = new JLabel("Description:");
            date = new JLabel("Date:");
            valueField = new JTextField();
            typeField = new JTextField();
            descriptionField = new JTextField();
            dateField = new JTextField("yyyy-mm-dd");
            information.add(value);
            information.add(valueField);
            information.add(type);
            information.add(typeField);
            information.add(description);
            information.add(descriptionField);
            information.add(date);
            information.add(dateField);
            inputPanel.add(information);

            add = new JButton("Add donation");
            inputPanel.add(add);

            addDonationListener = new AddDonation();
            add.addActionListener(addDonationListener);
        }
    }

    public class AddingDonor implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            inputPanel.removeAll();
            inputPanel.revalidate();
            inputPanel.repaint();
            inputPanel.setLayout(new GridLayout(4, 1));

            g = new ButtonGroup();
            r1 = new JRadioButton("individual");
            r2 = new JRadioButton("family");
            r3 = new JRadioButton("business");
            r1.setSelected(true);
            g.add(r1);
            g.add(r2);
            g.add(r3);
            group = new JPanel();
            group.setLayout(new GridLayout(1, 3));
            group.add(r1);
            group.add(r2);
            group.add(r3);
            inputPanel.add(group);

            name = new JLabel("Name: ");
            email = new JLabel("Email: ");
            nameField = new JTextField();
            emailField = new JTextField();
            input = new JPanel();
            input.setLayout(new GridLayout(1, 2));
            input.add(name);
            input.add(nameField);
            input.add(email);
            input.add(emailField);
            inputPanel.add(input);

            profit = new JLabel("For profit: ");
            profitGroup = new ButtonGroup();
            p1 = new JRadioButton("yes");
            p2 = new JRadioButton("no");
            profitGroup.add(p1);
            profitGroup.add(p2);
            profitPanel = new JPanel();
            profitPanel.setLayout(new GridLayout(1, 3));
            profitPanel.add(profit);
            profitPanel.add(p1);
            profitPanel.add(p2);
            inputPanel.add(profitPanel);
            radioChangeListener = new RadioChange();
            r1.addActionListener(radioChangeListener);
            r2.addActionListener(radioChangeListener);
            r3.addActionListener(radioChangeListener);
            p1.setEnabled(false);
            p2.setEnabled(false);
            p2.setSelected(true);

            add = new JButton("Add donor");
            inputPanel.add(add);
            addDonorListener = new AddDonor();
            add.addActionListener(addDonorListener);
        }
    }

    // constructor
    public GUIDonationDriver() {
        setGraphics();
        donors = new Donor[1000];
        count = 0;
        nameSearch = true;
        firstTotal = true;
        firstShow = true;
    }

    // setting the initial graphic
    public void setGraphics() {
        Container myPane;
        Border blackline;

        blackline = BorderFactory.createLineBorder(Color.black);
        myPane = getContentPane();
        myPane.setLayout(new GridLayout(1, 3));

        buttonsPanel = new JPanel();
        inputPanel = new JPanel();
        listPanel = new JPanel();
        buttonsPanel.setBorder(blackline);
        inputPanel.setBorder(blackline);
        listPanel.setBorder(blackline);
        buttonsPanel.setLayout(new GridLayout(6, 1));
        listPanel.setLayout(new GridLayout(1, 1));
        myPane.add(buttonsPanel);
        myPane.add(inputPanel);
        myPane.add(listPanel);

        addDonor = new JButton("Add donor");
        addDonation = new JButton("Add donation");
        getTotal = new JButton("Get total for donor");
        getStatus = new JButton("Get status of donor");
        listDonations = new JButton("List donor's donations");
        listAllDonations = new JButton("List all donations");
        buttonsPanel.add(addDonor);
        buttonsPanel.add(addDonation);
        buttonsPanel.add(getTotal);
        buttonsPanel.add(getStatus);
        buttonsPanel.add(listDonations);
        buttonsPanel.add(listAllDonations);

        listTitle = new JLabel("Donations", SwingConstants.CENTER);
        listTitle.setVerticalAlignment(JLabel.TOP);
        listPanel.add(listTitle);

        newDonor = new AddingDonor();
        addDonor.addActionListener(newDonor);
        newDonation = new AddingDonation();
        addDonation.addActionListener(newDonation);
        getTotalListener = new GetTotal();
        getTotal.addActionListener(getTotalListener);
        getStatusListener = new GetStatus();
        getStatus.addActionListener(getStatusListener);
        listDonationsListener = new ListDonations();
        listDonations.addActionListener(listDonationsListener);
        listAllDonationsListener = new ListAllDonations();
        listAllDonations.addActionListener(listAllDonationsListener);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setVisible(true);
    }
}