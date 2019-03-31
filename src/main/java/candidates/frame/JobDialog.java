package candidates.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import candidates.logic.Job;
import candidates.logic.ManagementSystem;

public class JobDialog extends JDialog implements ActionListener {

    private static final int D_HEIGHT = 250;   // высота окна
    private final static int D_WIDTH = 450;   // ширина окна
    private final static int L_X = 10;      // левая граница метки для поля
    private final static int L_W = 100;     // ширина метки для поля
    private final static int C_W = 150;     // ширина поля
    private CandidatesFrame owner;
    // Результат нажатия кнопок
    private boolean result = false;

    private int jobId = 0;
    private JTextField jobName = new JTextField();
    private JTextField companyName = new JTextField();
    private JTextField salary = new JTextField();

    public JobDialog(boolean newJob, CandidatesFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных вакансии");
        getContentPane().setLayout(new FlowLayout());
        getContentPane().setLayout(null);
        JLabel l = new JLabel("Вакансия:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        jobName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(jobName);
        // компания
        l = new JLabel("Компания:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        companyName.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(companyName);
        // зарплата
        l = new JLabel("Зарплата:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        salary.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(salary);
        salary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    long number = Long.parseLong(salary.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Only Numbers Allowed");
                    salary.setText("");
                }
            }
        });

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

        if (newJob) {
            JButton btnNew = new JButton("New");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 70, 100, 25);
            getContentPane().add(btnNew);
        }
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - JobDialog.D_WIDTH) / 2, ((int) d.getHeight() - JobDialog.D_HEIGHT) / 2,
                JobDialog.D_WIDTH, JobDialog.D_HEIGHT);
    }

    public void setJob(Job jb) {
        jobId = jb.getJobId();
        jobName.setText(jb.getJobName());
        companyName.setText(jb.getCompanyName());
        salary.setText(String.valueOf(jb.getSalary()));
    }

    public Job getJob() {
        Job jb = new Job();
        jb.setJobId(jobId);
        jb.setJobName(jobName.getText());
        jb.setCompanyName(companyName.getText());
        jb.setSalary(Double.parseDouble(salary.getText()));
        return jb;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
                ManagementSystem.getInstance().insertJob(getJob());
                owner.reloadJobs();
                jobName.setText("");
                companyName.setText("");
                salary.setText("");
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
