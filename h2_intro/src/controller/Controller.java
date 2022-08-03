package controller;

import input.IReader;
import model.Company;
import model.ICompanyDao;
import view.IPrinter;


public class Controller {

    private IPrinter view;
    private IReader input;
    private ICompanyDao manipulate;
    private State state;

    private final String MAIN_MENU =
            "\n1. Log in as a manager\n" +
                    "0. Exit\n";

    private final String ITEM1_MENU =
            "\n1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back\n";

    private final String LIST_EMPTY =
            "\nThe company list is empty!\n";

    private final String ENTER_COMPANY =
            "\nEnter the company name:\n";

    private final String CREATED =
            "The company was created!\n";

    private final String LIST =
            "Company list:\n";

    public void init(IReader input, IPrinter view, ICompanyDao manipulate) {
        this.input = input;
        this.view = view;
        this.manipulate = manipulate;
    }

    public void run() {
        state = State.MAIN;

        while (state != State.ITEM_0_EXIT) {
            setState(stateAction());
        }
    }

    private void setState(int val) {
        switch (state) {
            case MAIN:
                if (val == 0) {
                    state = State.ITEM_0_EXIT;
                } else if (val == 1) {
                    state = State.ITEM_1;
                }
                break;
            case ITEM_1:
                if (val == 0) {
                    state = State.ITEM_1_0_BACK;
                } else if (val == 1) {
                    state = State.ITEM_1_1;
                } else if (val == 2) {
                    state = State.ITEM_1_2;
                }
                break;
            case ITEM_1_1:
            case ITEM_1_2:
                state = State.ITEM_1;
                break;
            case ITEM_1_0_BACK:
                state = State.MAIN;
                break;
            default:
                break;
        }

    }

    private int stateAction() {
        int val = -1;
        switch (state) {
            case MAIN:
                view.print(MAIN_MENU);
                val = input.readInt();
                break;
            case ITEM_1:
                view.print(ITEM1_MENU);
                val = input.readInt();
                break;
            case ITEM_1_1:
                if (manipulate.getAllCompanies().size() > 0) {
                    view.print(LIST);
                    for(int i = 0; i < manipulate.getAllCompanies().size(); i++) {
                        String s = String.format("%d. %s\n",
                                manipulate.getAllCompanies().get(i).getId(),
                                manipulate.getAllCompanies().get(i).getName());
                        view.print(s);
                    }
                } else {
                    view.print(LIST_EMPTY);
                }
                break;
            case ITEM_1_2:
                view.print(ENTER_COMPANY);
                Company company = new Company();
                company.setId(manipulate.getAllCompanies().size() + 1);
                company.setName(input.readText());
                manipulate.insertCompany(company);
                view.print(CREATED);
                break;
            case ITEM_0_EXIT:
                manipulate.done();
                break;
        }
        return val;
    }
}
