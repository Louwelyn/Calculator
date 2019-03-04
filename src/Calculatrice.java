import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculatrice extends JFrame {
    private JPanel container = new JPanel();
    //Tableau stockant les éléments à afficher dans la calculatrice
    String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};

    //Un bouton par élément à afficher
    JButton[] tab_button = new JButton[tab_string.length];
    private JLabel ecran = new JLabel();
    private JPanel panEcran = new JPanel();

    private double nbre1=0;
    double nbre2=0;
    private double resultat;
    private boolean clearAll = true; // Est ce qu'on doit effacer l'affichage?
    private String operateur;


    public Calculatrice() {
        this.setSize(260, 300);
        this.setTitle("Calculette");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        //On initialise le conteneur avec tous les composants
        initComposant();
        //On ajoute le conteneur
        this.setContentPane(container);
        this.setVisible(true);
    }

    private void initComposant() {
        ecran.setText("0");
        ecran.setFont(new Font("Arial", Font.BOLD, 20));
        ecran.setHorizontalAlignment(SwingConstants.RIGHT);
        ecran.setPreferredSize(new Dimension(180, 30));
        panEcran.add(ecran);
        panEcran.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel panChiffre = new JPanel();
        panChiffre.setPreferredSize(new Dimension(140, 180));
        panChiffre.setLayout(new GridLayout(4, 3, 5, 5));

        JPanel panOperateur = new JPanel();
        panOperateur.setPreferredSize(new Dimension(45, 180));
        panOperateur.setLayout(new GridLayout(5, 1, 5, 5));

        for (int i = 0; i < tab_string.length; i++) {
            tab_button[i] = new JButton();
            tab_button[i].setText(tab_string[i]);
            // Ajout aux panels et listener  pour chiffre ou Ope
            if (i < 11) {
                panChiffre.add(tab_button[i]);
                tab_button[i].addActionListener(new ChiffreListener());
            } else if (i == 11) {
                panChiffre.add(tab_button[i]);
                tab_button[i].addActionListener(new EgalListener());
            } else if (i == 12) {
                panOperateur.add(tab_button[i]);
                tab_button[i].addActionListener(new ResetListener());
            } else {
                panOperateur.add(tab_button[i]);
                tab_button[i].addActionListener(new OpeListener());
            }
            container.add(panEcran, BorderLayout.NORTH);
            container.add(panChiffre, BorderLayout.CENTER);
            container.add(panOperateur, BorderLayout.EAST);
        }
    }


    //Méthode permettant d'effectuer un calcul selon l'opérateur sélectionné
    private double calcul(double nb1, double nb2) {
        if (operateur==null)
            resultat=nbre1;
        else if (operateur=="+")
            resultat=nbre1+nbre2;
        else if (operateur=="-")
            resultat=nbre1-nbre2;
        else if (operateur=="*")
            resultat=nbre1*nbre2;
        else {
            resultat = nbre1 / nbre2; // pas de souci de Div0 car Double
            // Penser à implémenter un message autre pour la Div par Zero
        }
            return resultat;
    }

    //Listener utilisé pour les chiffres
    //Permet de stocker les chiffres et de les afficher
    class ChiffreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = ((JButton)e.getSource()).getText();
            if (clearAll) {
                clearAll = false;
            } else {
                str=ecran.getText()+str;
            }
            ecran.setText(str);
        }
    }


    //Listener affecté au bouton =
    class EgalListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (operateur==null) // Si aucune operation effectuée au préalable
                nbre1=Double.valueOf(ecran.getText());
            else
                nbre2=Double.valueOf(ecran.getText());
            calcul(nbre1,nbre2);
            clearAll=true;
            nbre1=resultat;
            ecran.setText(String.valueOf(resultat));
        }
    }


        //Listener affecté au bouton + - * /
        class OpeListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (operateur == null){ // Si 1ere opération
                    nbre1 = Double.valueOf(ecran.getText());
                    nbre2 = 0;
                    operateur = ((JButton) e.getSource()).getText();
                } else {
                    nbre2=Double.valueOf(ecran.getText());
                    nbre1 = calcul(nbre1,nbre2);
                    operateur = ((JButton) e.getSource()).getText();
                    ecran.setText(String.valueOf(nbre1));
                }
                clearAll=true;
            }
        }


        //Listener affecté au bouton de remise à zéro
        class ResetListener implements ActionListener {
            public void actionPerformed(ActionEvent arg0) {
                resultat=0;
                nbre1=0;
                nbre2=0;
                operateur=null;
                clearAll=true;
                ecran.setText("0");
            }
        }
    }
