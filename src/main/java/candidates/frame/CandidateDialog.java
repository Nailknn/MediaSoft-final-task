package candidates.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import candidates.logic.Candidate;
import candidates.logic.ManagementSystem;

public class CandidateDialog extends JDialog implements ActionListener {

    private static final int D_HEIGHT = 250;   // высота окна
    private final static int D_WIDTH = 450;   // ширина окна
    private final static int L_X = 10;      // левая граница метки для поля
    private final static int L_W = 100;     // ширина метки для поля
    private final static int C_W = 150;     // ширина поля
    private CandidatesFrame owner;
    private boolean result = false;
    // Параметры кандидата
    private int candidateId = 0;
    private JTextField firstName = new JTextField();
    private JTextField surName = new JTextField();
    private JTextField patronymic = new JTextField();
    private JSpinner dateOfBirth = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private ButtonGroup sex = new ButtonGroup();
    private JTextField phoneNumber = new JTextField();
    private JTextField jobId = new JTextField();

    public CandidateDialog(boolean newCandidate, CandidatesFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных кандидата");
        getContentPane().setLayout(new FlowLayout());
        JRadioButton m = new JRadioButton("Муж");
        JRadioButton w = new JRadioButton("Жен");
        m.setActionCommand("М");
        w.setActionCommand("Ж");
        sex.add(m);
        sex.add(w);
        getContentPane().setLayout(null);
        // Фамилия
        JLabel l = new JLabel("Фамилия:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        surName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(surName);
        // Имя
        l = new JLabel("Имя:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        firstName.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(firstName);
        // Отчество
        l = new JLabel("Отчество:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        patronymic.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(patronymic);
        // Пол
        l = new JLabel("Пол:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        m.setBounds(L_X + L_W + 10, 70, C_W / 2, 20);
        getContentPane().add(m);
        w.setBounds(L_X + L_W + 10 + C_W / 2, 70, C_W / 2, 20);
        w.setSelected(true);
        getContentPane().add(w);
        // Дата рождения
        l = new JLabel("Дата рождения:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        dateOfBirth.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(dateOfBirth);
        // Телефон
        l = new JLabel("Телефон:", JLabel.RIGHT);
        l.setBounds(L_X, 120, L_W, 20);
        getContentPane().add(l);
        phoneNumber.setBounds(L_X + L_W + 10, 120, C_W, 20);
        phoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    long number = Long.parseLong(phoneNumber.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Only Numbers Allowed");
                    phoneNumber.setText("");
                }
            }
        });
        getContentPane().add(phoneNumber);
        // Вакансии
        l = new JLabel("ID вакансии:", JLabel.RIGHT);
        l.setBounds(L_X, 150, L_W, 20);
        getContentPane().add(l);
        jobId.setBounds(L_X + L_W + 10, 150, C_W, 20);
        jobId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    long number = Long.parseLong(jobId.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Only Numbers Allowed");
                    jobId.setText("");
                }
            }
        });
        getContentPane().add(jobId);

        JButton btnOk = new JButton("OK");
        btnOk.setName("OK");
        btnOk.addActionListener(this);
        btnOk.setBounds(L_X + L_W + C_W + 10 + 50, 10, 100, 25);
        getContentPane().add(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 100, 25);
        getContentPane().add(btnCancel);

        if (newCandidate) {
            JButton btnNew = new JButton("New");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 70, 100, 25);
            getContentPane().add(btnNew);
        }
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - CandidateDialog.D_WIDTH) / 2, ((int) d.getHeight() - CandidateDialog.D_HEIGHT) / 2,
                CandidateDialog.D_WIDTH, CandidateDialog.D_HEIGHT);
    }

    public void setCandidate(Candidate cd) {
        candidateId = cd.getCandidateId();
        firstName.setText(cd.getFirstName());
        surName.setText(cd.getSurName());
        patronymic.setText(cd.getPatronymic());
        dateOfBirth.getModel().setValue(cd.getDateOfBirth());
        for (Enumeration e = sex.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            ab.setSelected(ab.getActionCommand().equals(new String("" + cd.getSex())));
        }
        phoneNumber.setText(String.valueOf(cd.getPhonenumber()));
        jobId.setText(String.valueOf(cd.getJobId()));
    }

    public Candidate getCandidate() {
        Candidate cd = new Candidate();
        cd.setCandidateId(candidateId);
        cd.setFirstName(firstName.getText());
        cd.setSurName(surName.getText());
        cd.setPatronymic(patronymic.getText());
        Date d = ((SpinnerDateModel) dateOfBirth.getModel()).getDate();
        cd.setDateOfBirth(d);
        cd.setPhonenumber(Integer.parseInt(phoneNumber.getText()));
        cd.setJobId(Integer.parseInt(jobId.getText()));
        for (Enumeration e = sex.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            if (ab.isSelected()) {
                cd.setSex(ab.getActionCommand().charAt(0));
            }
        }
        return cd;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
                ManagementSystem.getInstance().insertCandidate(getCandidate());
                owner.reloadCandidates();
                firstName.setText("");
                surName.setText("");
                patronymic.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}
