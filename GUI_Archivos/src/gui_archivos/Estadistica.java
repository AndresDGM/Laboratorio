package gui_archivos;

import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
/**
 *
 * @author Andres Guevara
 * @author Camilo Suarez
 * @author Juan Estevan Santiago
 * @author Angie Cobo
 */
public class Estadistica extends JFrame{
    private Menu m;
    private JLabel titulo = new JLabel("Estadisticas");
    public Estadistica(Menu m){
        super("Estadisticas");
        this.m=m;
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        titulo.setBounds(450, 25, 100, 50);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setVerticalAlignment(JLabel.CENTER);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        add(titulo);
        setVisible(true);
        DefaultCategoryDataset datasetJuegoFav = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetSexo = new DefaultCategoryDataset();
        //añadir el de edad
        
        LeerFile(datasetSexo,datasetJuegoFav /*añadir el otro*/);
        
        JFreeChart chartSexo = ChartFactory.createBarChart(
        "Sexo de los Usuarios",
        "Sexo",
        "Cantidad",
        datasetSexo,
        PlotOrientation.VERTICAL,
        true,
        true,
        true);
        
        JFreeChart chartJuegoFav = ChartFactory.createBarChart(
        "Juego favorito de los Usuarios",
        "Juego Favorito",
        "Cantidad",
        datasetJuegoFav,
        PlotOrientation.VERTICAL,
        true,
        true,
        true);
        
        ChartPanel panelSexo = new ChartPanel(chartSexo,false);
        ChartPanel panelJuegoFav = new ChartPanel(chartJuegoFav,false);
        //la otra mierda esa
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sexo",panelSexo);
        tabbedPane.addTab("Juego Favorito", panelJuegoFav);
        
        add(tabbedPane);
        
        pack();
        setLocationRelativeTo(null);
        
    }
    public void LeerFile(DefaultCategoryDataset datasetSexo,DefaultCategoryDataset datasetJuegoFav /*añadir el otro*/){
        FileReader fr= null;
        boolean error = false;
        try {
            fr = new FileReader("datosJugadores.csv");
        }catch(Exception e){
            error = true;
            JOptionPane.showMessageDialog(null, "error al abrir el archivo");
        }
        if(!error){
            BufferedReader br = new BufferedReader(fr);
            String linea= "",tokens[];
            int[] contadoresJuegoFav = new int[5];
            int[] contadoresSexo = new int[4];
            //agregar el contador del otro caso
            
            try {
                while((linea = br.readLine())!=null){
                    tokens = linea.split(";");
                    switch(tokens[7]){
                        case "Ninguno":
                            contadoresSexo[0]++;
                            break;
                        case "Mujer":
                            contadoresSexo[1]++;
                            break;
                        case "Hombre":
                            contadoresSexo[2]++;
                            break;
                        case "Otro":
                            contadoresSexo[3]++;
                            break;
                    }
                    switch(tokens[8]){
                        case "Ninguno":
                            contadoresJuegoFav[0]++;
                            break;
                        case "Undertale":
                            contadoresJuegoFav[1]++;
                            break;
                        case "Call of Duty":
                            contadoresJuegoFav[2]++;
                            break;
                        case "World of warcraft":
                            contadoresJuegoFav[3]++;
                            break;
                        case "Minecraft":
                            contadoresJuegoFav[4]++;
                            break;
                    }
                    
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Error al leer el archivo");
            }
            datasetSexo.setValue(contadoresSexo[0], "Sexo","Ninguno");
            datasetSexo.setValue(contadoresSexo[1], "Sexo", "Mujer");
            datasetSexo.setValue(contadoresSexo[2], "Sexo", "Hombre");
            datasetSexo.setValue(contadoresSexo[3], "Sexo", "Otro");
            
            datasetJuegoFav.setValue(contadoresJuegoFav[0], "Juego Favorito", "Ninguno");
            datasetJuegoFav.setValue(contadoresJuegoFav[1], "Juego Favorito", "Undertale");
            datasetJuegoFav.setValue(contadoresJuegoFav[2], "Juego Favorito", "Call of Duty");
            datasetJuegoFav.setValue(contadoresJuegoFav[3], "Juego Favorito", "World of warcraft");
            datasetJuegoFav.setValue(contadoresJuegoFav[4], "Juego Favorito", "Minecraft");
            try{
                fr.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al cerrar el archivo");
            }
        }
    }
}
