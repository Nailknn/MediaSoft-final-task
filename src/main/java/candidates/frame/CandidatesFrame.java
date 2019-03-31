package candidates.frame;

import java.sql.SQLException;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import candidates.logic.Candidate;
import candidates.logic.Job;
import candidates.logic.ManagementSystem;
import candidates.logic.Interview;


public class CandidatesFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    private static final String INSERT_CN = "insertCandidate";
    private static final String UPDATE_CN = "updateCandidate";
    private static final String DELETE_CN = "deleteCandidate";

    private static final String INSERT_JB = "insertJob";
    private static final String UPDATE_JB = "updateJob";
    private static final String DELETE_JB = "deleteJob";

    private static final String INSERT_IN = "insertInterview";
    private static final String UPDATE_IN = "updateInterview";
    private static final String DELETE_IN = "deleteInterview";

    private ManagementSystem ms = null;
    private JTable jobsList;
    private JTable cndList;
    private JTable inwList;

    public CandidatesFrame() throws Exception {
        getContentPane().setLayout(new BorderLayout());

        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());

        JPanel centr = new JPanel();
        centr.setLayout(new BorderLayout());
        centr.setBorder(new BevelBorder(BevelBorder.LOWERED));

        ms = ManagementSystem.getInstance();
        // список вакансий
        Vector<Job> jb = new Vector<Job>(ms.getJobs());
        centr.add(new JLabel("Вакансии:"), BorderLayout.NORTH);
        jobsList = new JTable(1,3);
        centr.add(new JScrollPane(jobsList), BorderLayout.CENTER);
        // кнопки для вакансий
        JButton btnAddJb = new JButton("Добавить");
        btnAddJb.setName(INSERT_JB);
        btnAddJb.addActionListener(this);
        JButton btnUpdJb = new JButton("Исправить");
        btnUpdJb.setName(UPDATE_JB);
        btnUpdJb.addActionListener(this);
        JButton btnDelJb = new JButton("Удалить");
        btnDelJb.setName(DELETE_JB);
        btnDelJb.addActionListener(this);

        JPanel pnlBtnJb = new JPanel();
        pnlBtnJb.setLayout(new GridLayout(1, 2));
        pnlBtnJb.add(btnAddJb);
        pnlBtnJb.add(btnUpdJb);
        pnlBtnJb.add(btnDelJb);
        centr.add(pnlBtnJb, BorderLayout.SOUTH);

        // правая панель для вывода списка кандидатов
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.setBorder(new BevelBorder(BevelBorder.LOWERED));

        right.add(new JLabel("Кандидаты:"), BorderLayout.NORTH);

        cndList = new JTable(1, 4);
        right.add(new JScrollPane(cndList), BorderLayout.CENTER);
        // кнопки для кандидатов
        JButton btnAddCn = new JButton("Добавить");
        btnAddCn.setName(INSERT_CN);
        btnAddCn.addActionListener(this);
        JButton btnUpdSt = new JButton("Исправить");
        btnUpdSt.setName(UPDATE_CN);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Удалить");
        btnDelSt.setName(DELETE_CN);
        btnDelSt.addActionListener(this);

        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddCn);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

        // левая панель для вывода списка интервью
        JPanel left = new JPanel();

        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.LOWERED));

        left.add(new JLabel("Собеседование:"), BorderLayout.NORTH);

        inwList = new JTable(1,4);
        left.add(new JScrollPane(inwList), BorderLayout.CENTER);
        // кнопки для Интервью
        JButton btnAddIn = new JButton("Добавить");
        btnAddIn.setName(INSERT_IN);
        btnAddIn.addActionListener(this);
        JButton btnUpdIn = new JButton("Исправить");
        btnUpdIn.setName(UPDATE_IN);
        btnUpdIn.addActionListener(this);
        JButton btnDelIn = new JButton("Удалить");
        btnDelIn.setName(DELETE_IN);
        btnDelIn.addActionListener(this);

        JPanel pnlBtnIn = new JPanel();
        pnlBtnIn.setLayout(new GridLayout(1, 2));
        pnlBtnIn.add(btnAddIn);
        pnlBtnIn.add(btnUpdIn);
        pnlBtnIn.add(btnDelIn);
        left.add(pnlBtnIn, BorderLayout.SOUTH);

        bot.add(left, BorderLayout.WEST);
        bot.add(centr, BorderLayout.CENTER);
        bot.add(right, BorderLayout.EAST);

        getContentPane().add(bot, BorderLayout.CENTER);

        setBounds(20, 100, 1300, 500);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(INSERT_CN)) {
                insertCandidate();
            }
            if (c.getName().equals(UPDATE_CN)) {
                updateCandidate();
            }
            if (c.getName().equals(DELETE_CN)) {
                deleteCandidate();
            }
            if (c.getName().equals(INSERT_JB)) {
                insertJob();
            }
            if (c.getName().equals(UPDATE_JB)) {
                updateJob();
            }
            if (c.getName().equals(DELETE_JB)){
                deleteJob();
            }
            if (c.getName().equals(INSERT_IN)){
                insertInterview();
            }
            if (c.getName().equals(UPDATE_IN)){
                updateInterview();
            }
            if (c.getName().equals(DELETE_IN)){
                deleteInterview();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadCandidates();
            reloadJobs();
            reloadInterviews();
        }
    }

    public void stateChanged(ChangeEvent e) {
        reloadCandidates();
        reloadJobs();
        reloadInterviews();
    }

    // метод для обновления списка кандидатов
    public void reloadCandidates() {
        Thread t = new Thread() {
            // Переопределяем в нем метод run

            public void run() {
                if (cndList != null) {
                    try {
                        Collection<Candidate> c = ms.getCandidates();
                        cndList.setModel(new CandidateTableModel(new Vector<Candidate>(c)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                    }
                }
            }
            // Окончание нашего метода run
        };
        t.start();
    }

    // метод для обновления списка вакансий
    public void reloadJobs() {
        Thread t = new Thread() {
            // Переопределяем в нем метод run

            public void run() {
                if (jobsList != null) {
                    try {
                        Collection<Job> jb = ms.getJobs();
                        jobsList.setModel(new JobTableModel(new Vector<Job>(jb)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }

    // метод для обновления списка собеседования
    public void reloadInterviews() {
        Thread t = new Thread() {
            // Переопределяем в нем метод run

            public void run() {
                if (inwList != null) {
                    try {
                        Collection<Interview> iw = ms.getInterviews();
                        inwList.setModel(new InterviewTableModel(new Vector<Interview>(iw)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                    }
                }
            }
        };

        t.start();
    }

    // метод для добавления кандидата
    private void insertCandidate() {
        Thread t = new Thread() {

            public void run() {
                try {
                    CandidateDialog cd = new CandidateDialog(true, CandidatesFrame.this);
                    cd.setModal(true);
                    cd.setVisible(true);
                    if (cd.getResult()) {
                        Candidate c = cd.getCandidate();
                        ms.insertCandidate(c);
                        reloadCandidates();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    // метод для редактирования кандидата
    private void updateCandidate() {
        Thread t = new Thread() {

            public void run() {
                if (cndList != null) {
                    CandidateTableModel stm = (CandidateTableModel) cndList.getModel();
                    if (cndList.getSelectedRow() >= 0) {
                        Candidate c = stm.getCandidate(cndList.getSelectedRow());
                        try {
                            CandidateDialog cd = new CandidateDialog(false, CandidatesFrame.this);
                            cd.setCandidate(c);
                            cd.setModal(true);
                            cd.setVisible(true);
                            if (cd.getResult()) {
                                Candidate us = cd.getCandidate();
                                ms.updateCandidate(us);
                                reloadCandidates();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this,
                                "Необходимо выделить кандидата в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для редактирования вакансии
    private void updateJob() {
        Thread t = new Thread() {

            public void run() {
                if (jobsList != null) {
                    JobTableModel jtm = (JobTableModel) jobsList.getModel();
                    if (jobsList.getSelectedRow() >= 0) {
                        Job j = jtm.getJob(jobsList.getSelectedRow());
                        try {
                            JobDialog jd = new JobDialog(false, CandidatesFrame.this);
                            jd.setJob(j);
                            jd.setModal(true);
                            jd.setVisible(true);
                            if (jd.getResult()) {
                                Job us = jd.getJob();
                                ms.updateJob(us);
                                reloadJobs();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this,
                                "Необходимо выделить вакансию в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для редактирования собеседования
    private void updateInterview() {
        Thread t = new Thread() {

            public void run() {
                if (inwList != null) {
                    InterviewTableModel itm = (InterviewTableModel) inwList.getModel();
                    if (inwList.getSelectedRow() >= 0) {
                        Interview i = itm.getInterview(inwList.getSelectedRow());
                        try {
                            InterviewDialog id = new InterviewDialog(false, CandidatesFrame.this);
                            id.setInterview(i);
                            id.setModal(true);
                            id.setVisible(true);
                            if (id.getResult()) {
                                Interview us = id.getInterview();
                                ms.updateInterview(us);
                                reloadInterviews();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this,
                                "Необходимо выделить собеседование в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для удаления кандидата
    private void deleteCandidate() {
        Thread t = new Thread() {

            public void run() {
                if (cndList != null) {
                    CandidateTableModel ctm = (CandidateTableModel) cndList.getModel();
                    if (cndList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(CandidatesFrame.this,
                                "Вы хотите удалить кандидата?", "Удаление кандидата",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            Candidate c = ctm.getCandidate(cndList.getSelectedRow());
                            try {
                                ms.deleteCandidate(c);
                                reloadCandidates();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, "Необходимо выделить кандидата в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для удаления вакансии
    private void deleteJob() {
        Thread t = new Thread() {

            public void run() {
                if (jobsList != null) {
                    JobTableModel jtm = (JobTableModel) jobsList.getModel();
                    if (jobsList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(CandidatesFrame.this,
                                "Вы хотите удалить вакансию?", "Удаление вакансии",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            Job j = jtm.getJob(jobsList.getSelectedRow());
                            try {
                                ms.deleteJob(j);
                                reloadJobs();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, "Необходимо выделить вакансию в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для удаления вакансии
    private void deleteInterview() {
        Thread t = new Thread() {

            public void run() {
                if (inwList != null) {
                    InterviewTableModel itm = (InterviewTableModel) inwList.getModel();
                    if (inwList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(CandidatesFrame.this,
                                "Вы хотите удалить Собеседование?", "Удаление собеседования",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            Interview j = itm.getInterview(inwList.getSelectedRow());
                            try {
                                ms.deleteInterview(j);
                                reloadInterviews();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(CandidatesFrame.this, "Необходимо выделить собеседование в списке");
                    }
                }
            }
        };
        t.start();
    }

    // метод для добавления вакансии
    private void insertJob(){
        Thread t = new Thread() {

            public void run() {
                try {
                    JobDialog cd = new JobDialog(true, CandidatesFrame.this);
                    cd.setModal(true);
                    cd.setVisible(true);
                    if (cd.getResult()) {
                        Job j = cd.getJob();
                        ms.insertJob(j);
                        reloadJobs();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    // метод для добавления собеседования
    private void insertInterview() {
        Thread t = new Thread() {

            public void run() {
                try {
                    InterviewDialog iw = new InterviewDialog(true, CandidatesFrame.this);
                    iw.setModal(true);
                    iw.setVisible(true);
                    if (iw.getResult()) {
                        Interview j = iw.getInterview();
                        ms.insertInterview(j);
                        reloadInterviews();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(CandidatesFrame.this, e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    CandidatesFrame sf = new CandidatesFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    sf.setVisible(true);
                    sf.reloadCandidates();
                    sf.reloadJobs();
                    sf.reloadInterviews();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
