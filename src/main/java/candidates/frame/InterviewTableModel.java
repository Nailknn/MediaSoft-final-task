package candidates.frame;
import candidates.logic.Interview;
import candidates.logic.ManagementSystem;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class InterviewTableModel extends AbstractTableModel {

    private Vector interviews;
    private ManagementSystem ms = null;

    public InterviewTableModel(Vector interviews) {
        this.interviews = interviews;
        try {
            ms = ManagementSystem.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRowCount() {
        if (interviews != null) {
            return interviews.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int column) {
        String[] colNames = {"время", "вакансия", "кандидат", "результат"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (interviews != null) {
            Interview jb = (Interview) interviews.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return jb.getTimeInterview();
                case 1:
                    return ms.getJob(jb.getJobId());
                case 2:
                    return ms.getCandidate(jb.getCandidateId());
                case 3:
                    return jb.getResult();
            }
        }
        return null;
    }

    public Interview getInterview(int rowIndex) {
        if (interviews != null) {
            if (rowIndex < interviews.size() && rowIndex >= 0) {
                return (Interview) interviews.get(rowIndex);
            }
        }
        return null;
    }
}
