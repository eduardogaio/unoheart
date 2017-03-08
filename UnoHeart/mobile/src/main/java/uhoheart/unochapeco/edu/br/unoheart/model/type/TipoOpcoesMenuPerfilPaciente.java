package uhoheart.unochapeco.edu.br.unoheart.model.type;

import android.app.Activity;
import android.content.Intent;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.view.ActivityPacienteAltura;

/**
 * Created by Eduardo on 12/01/2017.
 */

public enum TipoOpcoesMenuPerfilPaciente {
    ALTURA("0", "Altura", "Manter dados sobre altura") {
        @Override
        public void abrirActivity(Activity parent) {
            Intent intent = new Intent(parent.getBaseContext(), ActivityPacienteAltura.class);
//            intent.putExtra("ActivitySelecionarEquipe", pacienteEquipe);
            parent.startActivity(intent);
            parent.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        }
    },
    ALIMENTACAO("1", "Alimentação", "Informar dados sobre alimentação") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    DADOS_PESSOAIS("2", "Dados Pessoais", "Alterar dados pessoas") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    PARAMETRO("3", "Parâmetros", "Definições e parâmetros") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    PATOLOGIA("4", "Patologia", "Informar patologia") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    PESO("5", "Peso", "Manter dados sobre pes") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    POSICAO("6", "Posicao Predominante", "Informar posição predominante") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    },
    REPOUSO("7", "Repouso", "Informar horários de repouso") {
        @Override
        public void abrirActivity(Activity parent) {
            
        }
    };


    private final String codigo;
    private final String nome;
    private final String descricao;

    private TipoOpcoesMenuPerfilPaciente(String codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public static TipoOpcoesMenuPerfilPaciente parse(String value) {
        if (value != null) {
            for (TipoOpcoesMenuPerfilPaciente tipo : values()) {
                if (value.equalsIgnoreCase(tipo.getNome())) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }


    public abstract void abrirActivity(Activity parent);
}
