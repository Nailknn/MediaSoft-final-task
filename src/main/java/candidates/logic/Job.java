package candidates.logic;

public class Job {
    // поле ид компании
    private int jobId;
    //название вакансии
    private String jobName;
    //название компании
    private String companyName;
    // зарплата
    private double salary;

    // get/set для ID
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    // get/set для название вакансии
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    // get/set для название компании
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // get/set для зарплаты
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String toString() {
        return jobName;
    }
}
