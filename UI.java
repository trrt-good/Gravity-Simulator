import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.awt.Color;

public class UI extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private final int FPS = 60;

	private Color backgroundColor = new Color(0, 0, 0);
    private Color controlPanelBGColor = new Color(20, 20, 20);
    private final double defaultScale = 0.7;
	private double scale = defaultScale;
	private Point offsetPoint = new Point(0, 0);

    private Point clickPoint;
    private Point clickOffsetPoint;
    
    private Color debugLineColor = Color.RED;
    private Color debugStringColor = new Color(100, 100, 255);
    private Color debugGravLine = Color.GREEN;
    private Color selectedObjColor = Color.MAGENTA;
    private boolean debugLines = true;

    private JSlider diameter;
    private JSlider timeScale;

    private JCheckBox kinematic;
    private JCheckBox gravity;
    private JCheckBox freezeRotation;
    private JCheckBox showVelocities;

    private JLabel xPos;
    private JLabel yPos;

    private JLabel objLabel;

    private int objSelected = 0;

	private Timer uiTimer = new Timer(1000/FPS + 1, new ActionListener()
	{
		@Override
        public void actionPerformed(ActionEvent e) 
        {
            repaint();
        }
	});

	public UI()
	{
        setLayout(new BorderLayout());
		setBackground(backgroundColor);
		uiTimer.start();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);

        ControlPanel controlPan = new ControlPanel();
        add(controlPan, BorderLayout.SOUTH);
	}

    @Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        for (int i =0; i < GameManager.physicsObjects.size(); i++)
        {
            drawPhysicsObject(g, GameManager.physicsObjects.get(i), debugLines, debugLines, debugLines);
        }
        xPos.setText("X: " + (int)(GameManager.physicsObjects.get(objSelected).position.x));
        yPos.setText("Y: " + (int)(GameManager.physicsObjects.get(objSelected).position.y));
	}

	public void drawPhysicsObject(Graphics g, PhysicsObject physicsObject, boolean drawVelocity, boolean drawVelocityString, boolean drawGvector)
	{
		g.setColor(physicsObject.color);
        Point screenCoords = toScreenCoords(physicsObject.position, (int)physicsObject.diameter);
		g.fillOval(screenCoords.x, screenCoords.y, (int)(physicsObject.diameter*scale), (int)(physicsObject.diameter*scale));

        if (drawVelocity)
        {
            g.setColor(debugLineColor);
            g.drawLine(toScreenCoords(physicsObject.position).x, toScreenCoords(physicsObject.position).y, toScreenCoords(Vector2.add(physicsObject.position, Vector2.multiply(physicsObject.velocity, GameManager.timeScale/3) )).x, toScreenCoords(Vector2.add(physicsObject.position, Vector2.multiply(physicsObject.velocity, GameManager.timeScale/3))).y);
        }

        if (drawVelocityString)
        {
            g.setColor(debugStringColor);
            g.drawString(String.format("%.1f", physicsObject.velocity.getMagnitude()), toScreenCoords(physicsObject.position).x, toScreenCoords(physicsObject.position).y);
        }

        if (drawGvector)
        {
            Vector2[] gravityVectors = physicsObject.getGravityVectors();
            for (int i = 0; i < gravityVectors.length; i++)
            {
                g.setColor(debugGravLine);
                g.drawLine(toScreenCoords(physicsObject.position).x, toScreenCoords(physicsObject.position).y, toScreenCoords(Vector2.add(physicsObject.position, Vector2.multiply(gravityVectors[i], GameManager.timeScale/physicsObject.mass))).x, toScreenCoords(Vector2.add(physicsObject.position, Vector2.multiply(gravityVectors[i], GameManager.timeScale/physicsObject.mass))).y);
            }
        }
	}

	public Point toScreenCoords(Vector2 worldCoords)
	{
        Point screenPoint = new Point((int)(worldCoords.x*scale + getWidth()/2 - offsetPoint.x), (int)(getHeight()/2 - worldCoords.y*scale - offsetPoint.y));
        //System.out.println("World: " + worldCoords.toString() + "\nScreen: " + screenPoint.toString());
		return screenPoint;
    }

    public Point toScreenCoords(Vector2 worldCoords, int diameter)
    {
		Point screenPoint = new Point((int)(worldCoords.x*scale + getWidth()/2 - offsetPoint.x) - (int)(diameter/2*scale), (int)(getHeight()/2 - worldCoords.y*scale - offsetPoint.y) - (int)(diameter/2*scale));
        //System.out.println("World: " + worldCoords.toString() + "\nScreen: " + screenPoint.toString());
        return screenPoint;
    }

    public double getScale()
    {
        return scale;
    }

    public Point getOffsePoint()
    {
        return offsetPoint;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        requestFocusInWindow();
        clickPoint = e.getPoint();
        clickOffsetPoint = offsetPoint;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        GameManager.timeScale += 1;
        System.out.println(GameManager.timeScale);
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        offsetPoint = new Point(clickPoint.x - e.getPoint().x + clickOffsetPoint.x, clickPoint.y - e.getPoint().y + clickOffsetPoint.y);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (e.getUnitsToScroll() != 0)
            scale -= 0.1*(e.getUnitsToScroll()/Math.abs(e.getUnitsToScroll()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    public class ControlPanel extends JPanel implements ActionListener, ChangeListener
    {
        public ControlPanel()
        {
            GameManager.physicsObjects.get(0).color = selectedObjColor;

            setLayout(new BorderLayout());
            JPanel centerGrid = new JPanel();
            centerGrid.setLayout(new GridLayout(1, 4));
            centerGrid.setBackground(controlPanelBGColor);
            setBackground(controlPanelBGColor);
            setPreferredSize(new Dimension(1920, 170));

            JPanel buttonAndMenu1 = new JPanel();
            buttonAndMenu1.setPreferredSize(new Dimension(460, 120));
            JPanel btnAndMenuMaster = new JPanel();
            btnAndMenuMaster.setPreferredSize(new Dimension(130, 120));
            btnAndMenuMaster.setBackground(controlPanelBGColor);
            buttonAndMenu1.setBackground(controlPanelBGColor);
            JButton resetAll = makeButton("Reset All");
            JButton resetPos = makeButton("Reset Positions");
            JMenuBar objectSelection = makeMenuBar();
            btnAndMenuMaster.add(resetAll);
            btnAndMenuMaster.add(resetPos);
            JPanel spacePan = new JPanel();
            spacePan.setBackground(controlPanelBGColor);
            spacePan.setPreferredSize(new Dimension(120, 1));
            btnAndMenuMaster.add(spacePan);
            btnAndMenuMaster.add(objectSelection);
            buttonAndMenu1.add(btnAndMenuMaster);

            JPanel sliders2 = new JPanel();
            sliders2.setBackground(controlPanelBGColor);
            sliders2.setLayout(new GridLayout(2, 1));
            JPanel diameterPanel = new JPanel();
            diameterPanel.setBackground(controlPanelBGColor);;
            JPanel timeScalePanel = new JPanel();
            timeScalePanel.setBackground(controlPanelBGColor);
            JLabel diam = new JLabel("Diameter");
            diam.setForeground(Color.WHITE);
            JLabel timeSc = new JLabel("Timescale");
            timeSc.setForeground(Color.WHITE);
            diameter = makeSlider(JSlider.HORIZONTAL, 1, 200, (int)(GameManager.physicsObjects.get(objSelected).diameter), controlPanelBGColor, 30);
            diameter.setPreferredSize(new Dimension(220, 50));
            timeScale = makeSlider(JSlider.HORIZONTAL, 0, 200, (int)(GameManager.timeScale), controlPanelBGColor, 40);
            timeScale.setPreferredSize(new Dimension(220, 50));

            diameterPanel.add(diam);
            diameterPanel.add(diameter);
            sliders2.add(diameterPanel);
            timeScalePanel.add(timeSc);
            timeScalePanel.add(timeScale);
            sliders2.add(timeScalePanel);

            JPanel checkBoxes3 = makeCheckBoxes();

            JPanel position4 = new JPanel();
            JPanel labelsMaster = new JPanel();
            labelsMaster.setPreferredSize(new Dimension(70, 120));
            labelsMaster.setBackground(controlPanelBGColor);
            JLabel position = new JLabel("Position");
            xPos = new JLabel("X: " + GameManager.physicsObjects.get(objSelected).position.x);
            yPos = new JLabel("Y: " + GameManager.physicsObjects.get(objSelected).position.y);
            position.setForeground(Color.WHITE);
            xPos.setForeground(Color.WHITE);
            yPos.setForeground(Color.WHITE);
            labelsMaster.add(position);
            labelsMaster.add(xPos);
            labelsMaster.add(yPos);

            position4.add(labelsMaster);
            position4.setPreferredSize(new Dimension(460, 120));
            position4.setBackground(controlPanelBGColor);

            centerGrid.add(buttonAndMenu1);
            centerGrid.add(sliders2);
            centerGrid.add(checkBoxes3);
            centerGrid.add(position4);

            objLabel = new JLabel("Selected Object: " + (objSelected + 1));
            objLabel.setForeground(Color.ORANGE);
            objLabel.setHorizontalAlignment(JLabel.CENTER);
            objLabel.setFont(new Font("Dialog", Font.BOLD, 15));
            objLabel.setBorder(BorderFactory.createEmptyBorder(7, 7, 0, 0));

            add(objLabel, BorderLayout.NORTH);
            add(centerGrid, BorderLayout.CENTER);
        }

        public JButton makeButton(String name)
        {
            JButton button = new JButton(name);
            button.addActionListener(this);
            button.setFocusPainted(true);

            return button;
        }

        public JMenuBar makeMenuBar()
        {
            JMenuBar bar = new JMenuBar();
            JMenu selection = new JMenu("Select Object");

            for (int i = 0; i < GameManager.physicsObjects.size(); i++)
            {
                JMenuItem object = new JMenuItem("Object #" + (i + 1));
                selection.add(object);
                object.addActionListener(this);
            }

            bar.add(selection);

            return bar;
        }

        public JSlider makeSlider(int orient, int min, int max, int initialValue, Color bgColor, int tickSpaces)
        {
            JSlider slider = new JSlider(orient, min, max, initialValue)
            {
                @Override
                public void updateUI()
                {
                    setUI(new SliderUI.CustomSliderUI(this));
                }
            };
            slider.setBackground(bgColor);
            slider.setForeground(Color.WHITE);
            slider.setMajorTickSpacing(tickSpaces);
            slider.setPaintTicks(true);
            slider.setLabelTable(slider.createStandardLabels(tickSpaces));
            slider.setPaintLabels(true);
            slider.addChangeListener(this);

            return slider;
        }

        public JPanel makeCheckBoxes()
        {
            JPanel checkBoxPanel = new JPanel();
            checkBoxPanel.setPreferredSize(new Dimension(460, 120));
            checkBoxPanel.setBackground(controlPanelBGColor);

            kinematic = new JCheckBox("Kinematic");
            gravity = new JCheckBox("Gravity");
            freezeRotation = new JCheckBox("Freeze Rotation");
            showVelocities = new JCheckBox("Show Velocities");

            kinematic.setBackground(controlPanelBGColor);
            gravity.setBackground(controlPanelBGColor);
            freezeRotation.setBackground(controlPanelBGColor);
            showVelocities.setBackground(controlPanelBGColor);

            checkBoxPanel.add(kinematic);
            checkBoxPanel.add(gravity);
            checkBoxPanel.add(freezeRotation);
            checkBoxPanel.add(showVelocities);

            kinematic.addActionListener(this);
            gravity.addActionListener(this);
            freezeRotation.addActionListener(this);
            showVelocities.addActionListener(this);

            kinematic.setSelected(GameManager.physicsObjects.get(objSelected).isKinematic);
            gravity.setSelected(GameManager.physicsObjects.get(objSelected).effectedByGravity);
            freezeRotation.setSelected(GameManager.physicsObjects.get(objSelected).freezeRotation);
            showVelocities.setSelected(true);

            kinematic.setForeground(Color.WHITE);
            gravity.setForeground(Color.WHITE);
            freezeRotation.setForeground(Color.WHITE);
            showVelocities.setForeground(Color.WHITE);

            return checkBoxPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            PhysicsObject currentObj;
            String command = e.getActionCommand();
            if (command.equals("Reset All"))
            {
                for (int i = 0; i < GameManager.physicsObjects.size(); i++)
                {
                    currentObj = GameManager.physicsObjects.get(i);
                    currentObj.mass = currentObj.originalMass;
                    currentObj.position = new Vector2(currentObj.originalPosition);
                    currentObj.velocity = new Vector2(0, 0);
                    currentObj.isKinematic = currentObj.kinOG;
                    currentObj.freezeRotation = currentObj.frzRotOG;
                    currentObj.effectedByGravity = currentObj.effctGvtyOG;
                    currentObj.collisions = currentObj.collsnsOG;
                    currentObj.diameter = GameManager.originalDiameters.get(i);
                    offsetPoint = new Point(0, 0);
                    scale = defaultScale;

                    List<Vector2> keys = new ArrayList<Vector2>(GameManager.objForceInfo.keySet());
                    currentObj.addForce(new Vector2(keys.get(i)), GameManager.objForceInfo.get(keys.get(i)));
                }

                currentObj = GameManager.physicsObjects.get(objSelected);
                diameter.setValue((int)(currentObj.diameter));
                kinematic.setSelected(currentObj.isKinematic);
                gravity.setSelected(currentObj.effectedByGravity);
                freezeRotation.setSelected(currentObj.freezeRotation);

                objLabel.setText("Selected Object: " + (objSelected + 1));
            }
            else if (command.equals("Reset Positions"))
            {
                for (int i = 0; i < GameManager.physicsObjects.size(); i++)
                {
                    currentObj = GameManager.physicsObjects.get(i);
                    currentObj.mass = currentObj.originalMass;
                    currentObj.position = new Vector2(currentObj.originalPosition);
                    currentObj.velocity = new Vector2(0, 0);

                    List<Vector2> keys = new ArrayList<Vector2>(GameManager.objForceInfo.keySet());
                    currentObj.addForce(new Vector2(keys.get(i)), GameManager.objForceInfo.get(keys.get(i)));
                }
            }
            else if (command.startsWith("Object #"))
            {
                objSelected = Integer.parseInt(command.substring(8)) - 1;
                currentObj = GameManager.physicsObjects.get(objSelected);

                for (int i = 0; i < GameManager.physicsObjects.size(); i ++)
                {
                    GameManager.physicsObjects.get(i).color = GameManager.physicsObjects.get(i).defaultColor;
                }
                currentObj.color = selectedObjColor;

                diameter.setValue((int)(currentObj.diameter));
                kinematic.setSelected(currentObj.isKinematic);
                gravity.setSelected(currentObj.effectedByGravity);
                freezeRotation.setSelected(currentObj.freezeRotation);
                showVelocities.setSelected(currentObj.collisions);

                objLabel.setText("Selected Object: " + (objSelected + 1));
            }
            else if (command.equals("Kinematic"))
            {
                GameManager.physicsObjects.get(objSelected).isKinematic = kinematic.isSelected();
            }
            else if (command.equals("Gravity"))
            {
                GameManager.physicsObjects.get(objSelected).effectedByGravity = gravity.isSelected();
            }
            else if (command.equals("Freeze Rotation"))
            {
                GameManager.physicsObjects.get(objSelected).freezeRotation = freezeRotation.isSelected();
            }
            else if (command.equals("Show Velocities"))
            {
                debugLines = showVelocities.isSelected();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e)
        {
            if (diameter == e.getSource())
            {
                GameManager.physicsObjects.get(objSelected).diameter = diameter.getValue();
                GameManager.physicsObjects.get(objSelected).mass = GameManager.physicsObjects.get(objSelected).originalMass + diameter.getValue()*diameter.getValue();
            }
            else
                GameManager.timeScale = timeScale.getValue();
        }
    }
}
