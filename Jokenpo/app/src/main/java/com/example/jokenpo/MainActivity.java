package com.example.jokenpo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void selecionarPedra(View view) {
        verificarGanhador("pedra");
    }
    public void selecionarPapel(View view) {
        verificarGanhador("papel");
    }
    public void selecionarTesoura(View view) {
        verificarGanhador("tesoura");
    }
    private String gerarEscolhaApp() {

        int numeroAletorio = new Random().nextInt(3);

        String[] opcoes = {"pedra", "papel", "tesoura"};

        ImageView imagem = findViewById(R.id.image_app);

        String escolhaApp = opcoes[numeroAletorio];

        switch (escolhaApp) {
            case "pedra":
                imagem.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imagem.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imagem.setImageResource(R.drawable.tesoura);
                break;
        }

        return escolhaApp;
    }
    private void verificarGanhador(String escolhaUsuario) {

        String escolhaApp = gerarEscolhaApp();

        TextView resultado = findViewById(R.id.text_resultado);

        if(
                (escolhaApp == "pedra" && escolhaUsuario == "tesoura") ||
                (escolhaApp == "papel" && escolhaUsuario == "pedra") ||
                (escolhaApp == "tesoura" && escolhaUsuario == "papel") ) {

            resultado.setText("Você Perdeu");
        }else if (
                (escolhaApp == "pedra" && escolhaUsuario == "papel") ||
                (escolhaApp == "papel" && escolhaUsuario == "tesoura") ||
                (escolhaApp == "tesoura" && escolhaUsuario == "pedra")) {

            resultado.setText("Você Ganhou");
        } else {
            resultado.setText("Empate");
        }
    }
}