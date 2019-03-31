package candidates.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.*;

import candidates.logic.Candidate;
import candidates.logic.Job;
import candidates.logic.ManagementSystem;
import candidates.logic.Interview;


public class InterviewDialog extends JDialog implements ActionListener {

    private ManagementSystem ms = null;

    private static final int D_HEIGHT = 200;   // высота окна
    private final static int D_WIDTH = 530;   // ширина окна
    private final static int L_X = 10;      // левая граница метки для поля
    private final static int L_W = 140;     // ширина метки для поля
    private final static int C_W = 180;     // ширина поля

    private CandidatesFrame owner;

    private boolean result = false;
    // Параметры собеседования
    private int interviewId = 0;
    private JSpinner timeInterview =new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
    private JComboBox jobsList;
    private JComboBox cndList;
    private JComboBox inwList;
    private ButtonGroup results = new ButtonGroup();

    public InterviewDialog(boolean newInterview, CandidatesFrame owner) throws Exception {
        ms = ManagementSystem.getInstance();

        this.owner = owner;
        // Установить заголовок
        setTitle("Редактирование данных собеседования");
        getContentPane().setLayout(new FlowLayout());

        getContentPane().setLayout(null);

        // Дата собеседования
        JLabel l = new JLabel("Дата собеседования:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        timeInterview.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(timeInterview);
        // Вакансия
        l = new JLabel("Вакансия:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        jobsList = new JComboBox(new Vector(ms.getJobs()));
        jobsList.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(jobsList);
        // Кандидат
        l = new JLabel("Кандидат:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        cndList = new JComboBox(new Vector(ms.getCandidates()));
        cndList.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(cndList);

        // Результат собеседования
        JRadioButton y = new JRadioButton("Принят");
        JRadioButton n = new JRadioButton("Отказано");
        // Сделаем имя такое же, как требуется в баще данных - М/Ж
        y.setActionCommand("True");
        n.setActionCommand("False");
        results.add(y);
        results.add(n);
        l = new JLabel("Результат:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        y.setBounds(L_X + L_W + 10, 70, C_W / 2, 20);
        getContentPane().add(y);
        n.setBounds(L_X + L_W + 10 + C_W / 2, 70, C_W / 2, 20);
        y.setSelected(true);
        getContentPane().add(n);

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

        if (newInterview) {
            JButton btnNew = new JButton("New");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 70, 100, 25);
            getContentPane().add(btnNew);
        }

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - InterviewDialog.D_WIDTH) / 2, ((int) d.getHeight() - InterviewDialog.D_HEIGHT) / 2,
                InterviewDialog.D_WIDTH, InterviewDialog.D_HEIGHT);
    }

    public void setInterview(Interview iw) {
        interviewId = iw.getInterviewId();
        timeInterview.getModel().setValue(iw.getTimeInterview());
        for (int i = 0; i < jobsList.getModel().getSize(); i++) {
            Job j = (Job) jobsList.getModel().getElementAt(i);
            if (j.getJobId() == iw.getJobId()) {
                jobsList.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cndList.getModel().getSize(); i++) {
            Candidate c = (Candidate) cndList.getModel().getElementAt(i);
            if (c.getJobId() == iw.getCandidateId()) {
                cndList.setSelectedIndex(i);
                break;
            }
        }
        for (Enumeration e = results.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            String res = iw.getResult() ? "True" : "False";
            ab.setSelected(ab.getActionCommand().equals(res));
        }
    }

    public Interview getInterview() {
        Interview iw = new Interview();
        iw.setInterviewId(interviewId);
        Date d = ((SpinnerDateModel) timeInterview.getModel()).getDate();
        iw.setTimeInterview(d);
        iw.setJobId(((Job) jobsList.getSelectedItem()).getJobId());
        iw.setCandidate(((Candidate) cndList.getSelectedItem()).getCandidateId());
        iw.setResult(results.getSelection().getActionCommand().equals("True"));

        return iw;
    }

    public boolean getResult() {
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
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
