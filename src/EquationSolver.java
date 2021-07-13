public class EquationSolver {

    private double[][] A;
    private double[] X;

    public EquationSolver(double[][] A, double[] X){
        this.A = A;
        this.X = X;
    }

    boolean checkExchange(int row) {
        int n = A.length;
        if (A[row][row] == 0) {
            for (int j = row + 1; j < n; j++) {
                if (A[row][j] != 0) {
                    double[] temp;
                    temp = A[row];
                    A[row] = A[j];
                    A[j] = temp;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    boolean solve() {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            if (!checkExchange(i))
                return false;
            for (int j = i + 1; j <= n; j++)
                A[i][j] /= A[i][i];
            for (int j = 0; j < n; j++) {
                if (j == i)
                    continue;
                for (int k = i + 1; k <= n; k++)
                    A[j][k] -= A[i][k] * A[j][i];
            }
        }
        for (int i = 0; i < n; i++)
            X[i] = A[i][n];
        return true;
    }


}
