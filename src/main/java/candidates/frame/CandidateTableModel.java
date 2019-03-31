package candidates.frame;

import candidates.logic.Candidate;

import java.text.DateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CandidateTableModel extends AbstractTableModel {

    private Vector candidates;

    public CandidateTableModel(Vector candidates) {
        this.candidates = candidates;
    }

    public int getRowCount() {
        if (candidates != null) {
            return candidates.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int column) {
        String[] colNames = {"Фамилия", "Имя", "Отчество", "Дата"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (candidates != null) {
            Candidate cd = (Candidate) candidates.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return cd.getSurName();
                case 1:
                    return cd.getFirstName();
                case 2:
                    return cd.getPatronymic();
                case 3:
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(
                            cd.getDateOfBirth());
            }
        }
        return null;
    }

    // метод который возвращает кандидата по номеру строки
    public Candidate getCandidate(int rowIndex) {
        if (candidates != null) {
            if (rowIndex < candidates.size() && rowIndex >= 0) {
                return (Candidate) candidates.get(rowIndex);
            }
        }
        return null;
    }
}
