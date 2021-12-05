package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // 첫 번째 입력값인지 확인
    boolean isFirstInput = true;

    // 연산자가 눌렸는지 확인
    boolean isOperatorClick = false;

    // 연산 결과값 저장 변수 초기화
    double resultNum = 0;

    // 입력 숫자 변수 초기화
    double inputNum = 0;

    // 연산자 저장 변수 초기화
    String operator = "=";

    // 마지막 연산자 변수 초기화
    String lastOperator = "＋";

    // 뷰바인딩 활용
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    // 숫자 제어
    public void numButtonClick(View view) {
        if(isFirstInput) {
            activityMainBinding.resultTextView.setText(view.getTag().toString());
            isFirstInput = false;
            if(operator.equals("=")) {
                activityMainBinding.mathTextView.setText("");
                isOperatorClick = false;
            }
        } else {
            // 0 예외 처리
            if(activityMainBinding.resultTextView.getText().toString().equals("0")) {
                Toast.makeText(this, "0으로 시작되는 숫자는 없습니다.", Toast.LENGTH_SHORT).show();
                isFirstInput = true;
            } else {
                activityMainBinding.resultTextView.append(view.getTag().toString());
            }
        }
    }

    // 연산자 제어
    public void operatorButtonClick(View view) {
        isOperatorClick = true;
        lastOperator = view.getTag().toString();

        // 연산자 수정
        if(isFirstInput) {
            if(operator.equals("=")) { // 연산 이어서 할 경우 결과값을 mathTextView에 띄워주기
                operator = view.getTag().toString();
                resultNum = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());
                activityMainBinding.mathTextView.setText(resultNum + " " + operator + " ");
            } else {
                operator = view.getTag().toString();
                String getMathText = activityMainBinding.mathTextView.getText().toString();
                String subString = getMathText.substring(0, getMathText.length() - 2); // 연산자 자르기
                activityMainBinding.mathTextView.setText(subString);
                activityMainBinding.mathTextView.append(operator + " ");
            }
        } else {
            inputNum = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());

            resultNum = calculator(resultNum, inputNum, operator);

            activityMainBinding.resultTextView.setText(String.valueOf(resultNum));
            isFirstInput = true;
            operator = view.getTag().toString(); // 연산자 받아와서 operator에 저장
            activityMainBinding.mathTextView.append(inputNum + " " + operator + " ");
        }
    }

    // AC 제어
    public void allClearButtonClick(View view) {
        activityMainBinding.resultTextView.setText("0");
        activityMainBinding.mathTextView.setText("");
        // 초기화
        resultNum = 0;
        operator = "=";
        isFirstInput = true;
        isOperatorClick = false;
    }

    // . 제어
    public void pointButtonClick(View view) {
        // . 예외 처리 - 처음에 . 눌렀을 때 0 추가
        if(isFirstInput) {
            activityMainBinding.resultTextView.setText("0" + view.getTag().toString());
            isFirstInput = false;
        } else {
            // . 예외 처리 - .은 여러 번 사용 불가
            if(activityMainBinding.resultTextView.getText().toString().contains(".")) {
                Toast.makeText(this, "이미 소숫점이 존재합니다.", Toast.LENGTH_SHORT).show();
            } else {
                activityMainBinding.resultTextView.append(view.getTag().toString());
            }
        }
    }

    // = 제어
    public void equalsButtonClick(View view) {
        // = 여러번 입력시 최근 연산으로 인식
        if(isFirstInput) {
            if(isOperatorClick)  { // = 중복 방지
                activityMainBinding.mathTextView.setText(resultNum + " " + lastOperator + " " + inputNum + " =");
                resultNum = calculator(resultNum, inputNum, lastOperator);
                activityMainBinding.resultTextView.setText(String.valueOf(resultNum));
            }
        } else {
            inputNum = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());

            resultNum = calculator(resultNum, inputNum, operator);

            activityMainBinding.resultTextView.setText(String.valueOf(resultNum));
            isFirstInput = true;
            operator = view.getTag().toString(); // 연산자 받아와서 operator에 저장
            activityMainBinding.mathTextView.append(inputNum + " " + operator + " ");
        }
    }

    // calculator 메소드
    private double calculator(double resultNum, double inputNum, String operator) {
        switch(operator) {
            case "＋":
                resultNum = resultNum + inputNum;
                break;
            case "－":
                resultNum = resultNum - inputNum;
                break;
            case "×":
                resultNum = resultNum * inputNum;
                break;
            case "÷":
                resultNum = resultNum / inputNum;
                break;
            case "=":
                resultNum = inputNum;
                break;
        }

        return resultNum;
    }
}