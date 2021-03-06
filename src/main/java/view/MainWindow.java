/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Coordinator;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import model.Line;
import model.OFGelement;
import model.XmlConversor;
import org.apache.commons.io.FilenameUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * This jframeForm is the first view of the UI
 *
 * @author victor del rio
 */
public class MainWindow extends javax.swing.JFrame implements MouseListener {
    private static final String folderWithOutSrc = "The selected folder does not match to a proyect.";
    private static final String invalidDirectory = "The selected path is not a valid directory.";
    private static final String invalidSintaxStructure = "Please select a valid structure.";
    private static final String selectAJavaFile = "Please select a java file.";
    private static final String selectAProject = "Please select a project.";
    private Coordinator coordinator;
    private RSyntaxTextArea textArea;
    private RTextScrollPane textScrollPane;
    private JPanel panel;
    private File projectFolder;
    private File lastSelectedFilePath;


    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        add(initTextArea());
        setTitle("TesisFinalProgram");
        setResizable(false);
        setLocationRelativeTo(null);
        jTree.setRootVisible(false);
        jTree.putClientProperty("JTree.lineStyle", "None");
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(createImageIcon("images/JavaIcon.png"));
        jTree.setCellRenderer(renderer);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {  
        public void valueChanged(TreeSelectionEvent e) {  
           TreePath treePath = e.getNewLeadSelectionPath();  
           if (treePath != null) {
              lastSelectedFilePath = ((MyFile)treePath.getLastPathComponent()).getFile();
           }
        }  
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    /**
     * Returns an ImageIcon, or null if the path was invalid. 
     * @param path
     * @return 
     */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    /**
     * Initializes the RSyntaxArea and enters it into a JPanel.
     * @return 
     */
    public JPanel initTextArea(){
        panel = new JPanel(new BorderLayout());
        panel.setBounds(310, 12,686, 555);
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setEditable(false);
        textArea.addMouseListener(this);
        textScrollPane = new RTextScrollPane(textArea);
        panel.add(textScrollPane);
        return panel;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree);

        jMenu1.setText("File");

        jMenuItem1.setText(" Open File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addGap(716, 716, 716))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            openFolder();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
        Object lastPathComponent = evt.getNewLeadSelectionPath().getLastPathComponent();
        if(lastPathComponent instanceof MyFile){
            MyFile myFile = (MyFile) lastPathComponent;
            File file = myFile.getFile();
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if(!file.isDirectory() && "java".equals(ext)){
                try {
                    textArea.setText(coordinator.readFileCode(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, selectAJavaFile);
            }
        } 
    }
    
    /**
     * Changes the path of the open folder to a local variable and makes a call to "defineTreeModel()"
     * @throws FileNotFoundException 
     */
    public void openFolder() throws FileNotFoundException{
        JFileChooser file = new JFileChooser();
        file.setAcceptAllFileFilterUsed(false);
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.showOpenDialog(this);
        File path = file.getSelectedFile();
        if(path != null && path.exists() && path.isDirectory()){
            defineTreeModel(path);
            projectFolder = path;
        } else {
            JOptionPane.showMessageDialog(null, selectAProject);
        }
    }
    
    /**
     * Takes the format of the tree model from the path and implements it to the jtree
     * @param file 
     */
    public void defineTreeModel(File file){
        boolean validFolder = false;
        String[] names = file.list();
        for(String name : names){
            if("src".equals(name)){
                validFolder = true;
            } 
        }
        if(validFolder){
            if (new File(file.getAbsolutePath() + "\\" + "src").isDirectory()){
                MyFile myFile = new MyFile(file);
                TreeModel model = new FileTreeModel(myFile);
                jTree.setModel(model);
                jTree.setRootVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, invalidDirectory);
            }
        } else {
                JOptionPane.showMessageDialog(null, folderWithOutSrc);
        }    
    }

    public void mouseClicked(MouseEvent e) {
            RSyntaxTextArea area = ((RSyntaxTextArea)e.getComponent());
            String[] text = area.getText().split("\n");
            Line line = coordinator.analyzeLineOfCode(text[area.getCaretLineNumber()]);
            line.setIndex(area.getCaretLineNumber());
            if(line.isState() && line.getType().equals("objectMethodInvocation")){
                try {
                    OFGView ofgView= new OFGView();
                    OFGelement ofg = coordinator.analyzeAllSourceCode( line, lastSelectedFilePath, projectFolder);
                    XmlConversor.createXmlDocument(ofg);
                    ofgView.setOfg(ofg);
                    ofgView.initGraph();
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, invalidSintaxStructure);
            }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree;
    // End of variables declaration//GEN-END:variables
}
