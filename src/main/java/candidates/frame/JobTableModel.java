package candidates.frame;
import candidates.logic.Job;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class JobTableModel extends AbstractTableModel {

    private Vector jobs;

    public JobTableModel(Vector jobs) {
        this.jobs = jobs;
    }

    public int getRowCount() {
        if (jobs != null) {
            return jobs.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int column) {
        String[] colNames = { "Вакансия","Компания","Зарплата",};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (jobs != null) {
            Job jb = (Job) jobs.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return jb.getJobName();
                case 1:
                    return jb.getCompanyName();
                case 2:
                    return jb.getSalary();
            }
        }
        return null;
    }

    // метод который возвращает вакансию по номеру строки
    public Job getJob(int rowIndex) {
        if (jobs != null) {
            if (rowIndex < jobs.size() && rowIndex >= 0) {
                return (Job) jobs.get(rowIndex);
            }
        }
        return null;
    }
}
