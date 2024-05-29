import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    private JLabel scoreLabel;

    private String[][] questions = {
            {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "Paris"},
            {"Who wrote 'Hamlet'?", "Shakespeare", "Tolstoy", "Hemingway", "Poe", "Shakespeare"},
            {"What is the largest planet in our solar system?", "Earth", "Mars", "Jupiter", "Saturn", "Jupiter"}
    };
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timer timer;
    private int timeRemaining = 10;

    public QuizApplication() {
        // Set up the frame
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        // Set up the question label
        questionLabel = new JLabel("");
        questionLabel.setBounds(50, 20, 300, 25);
        frame.add(questionLabel);

        // Set up the option buttons
        optionButtons = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton("");
            optionButtons[i].setBounds(50, 50 + (i * 30), 300, 25);
            buttonGroup.add(optionButtons[i]);
            frame.add(optionButtons[i]);
        }

        // Set up the submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 200, 100, 25);
        frame.add(submitButton);

        // Set up the timer label
        timerLabel = new JLabel("Time: " + timeRemaining);
        timerLabel.setBounds(50, 230, 100, 25);
        frame.add(timerLabel);

        // Set up the score label
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(200, 230, 100, 25);
        frame.add(scoreLabel);

        // Add action listener to the submit button
        submitButton.addActionListener(new SubmitButtonListener());

        // Start the quiz
        loadQuestion();

        // Make the frame visible
        frame.setVisible(true);
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex][0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questions[currentQuestionIndex][i + 1]);
            }
            buttonGroup.clearSelection();
            timeRemaining = 10;
            timerLabel.setText("Time: " + timeRemaining);
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText("Time: " + timeRemaining);
                } else {
                    timer.cancel();
                    checkAnswer();
                }
            }
        }, 1000, 1000);
    }

    private void checkAnswer() {
        String selectedAnswer = null;
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                selectedAnswer = button.getText();
                break;
            }
        }
        if (selectedAnswer != null && selectedAnswer.equals(questions[currentQuestionIndex][5])) {
            score++;
        }
        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;
        loadQuestion();
    }

    private void showResult() {
        timer.cancel();
        JOptionPane.showMessageDialog(frame, "Quiz over! Your score: " + score + "/" + questions.length);
        frame.dispose();
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            checkAnswer();
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}
