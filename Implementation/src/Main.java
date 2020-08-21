public class Main {
    /**
     * MAIN METHOD
     * Method to initialise the rest of the program. Shows a welcome message then generates a new game object
     *
     * @param args arguments
     * @author Vaibhav Ekambaram
     */
    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------------------\n" +
                "\tCluedo!\n" +
                "------------------------------------------------------------------------\n" +
                "\tSWEN225 Assignment 1\n" +
                "\tA group project by:\n" +
                "\t\tCameron Li\tVaibhav Ekambaram\tBaxter Kirikiri\n" +
                "------------------------------------------------------------------------\n");
        new View.Table();
        //new Model.Game();
    }
}
