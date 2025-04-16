package cadastro.desafio.model;

import cadastro.desafio.exceptions.IdadeException;
import cadastro.desafio.exceptions.NomeSobrenomeException;
import cadastro.desafio.exceptions.PesoException;

public class Pet {
    private String nomeSobrenome;
    private TipoPet tipoDePet;
    private TipoSexo tipoDeSexo;
    private Endereco enderecoCompleto;
    private double idade;
    private double peso;
    private String raca;



    @Override
    public String toString() {
        return  "1 - " + nomeSobrenome + "\n" +
                "2 - " + tipoDePet.getNomeTipoPet() + "\n" +
                "3 - " + tipoDeSexo.getSexo() + "\n" +
                "4 - " + enderecoCompleto.toString() + "\n" +
                "5 - " + idade + " anos" + "\n" +
                "6 - " + peso + "kg" + "\n" +
                "7 - " + raca;
    }

    public Pet() {
    }

    public Pet(String nomeSobrenome, TipoPet tipoDePet, TipoSexo tipoDeSexo, Endereco enderecoCompleto, double idade, double peso, String raca) {
        this.nomeSobrenome = nomeSobrenome;
        this.tipoDePet = tipoDePet;
        this.tipoDeSexo = tipoDeSexo;
        this.enderecoCompleto = enderecoCompleto;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public String getNomeSobrenome() {
        return nomeSobrenome;
    }


    public TipoPet getTipoPet() {
        return tipoDePet;
    }

    public TipoSexo getTipoDeSexo() {
        return tipoDeSexo;
    }

    public Endereco getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public double getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setNomeSobrenome(String nomeSobrenome) {
        String[] partes = nomeSobrenome.trim().split("\\s+", 2);
        if (partes.length < 2) {
            throw new NomeSobrenomeException("Formato inválido, digite nome e sobrenome do seu pet");
        } else if (!nomeSobrenome.matches("^[\\p{L}\\s]+$")) {
            throw new NomeSobrenomeException("O nome completo NÃO poderá conter caracteres especiais, somente letras de A-Z");
        }
        this.nomeSobrenome = nomeSobrenome;
    }

    public void setTipoPet(TipoPet tipoDePet) {
        this.tipoDePet = tipoDePet;
    }

    public void setTipoDeSexo(TipoSexo tipoDeSexo) {
        this.tipoDeSexo = tipoDeSexo;
    }

    public void setEnderecoCompleto(Endereco enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public void setIdade(double idade) {
        if (idade > 20) {
            throw new IdadeException("Idade inválida, por favor revise o valor informado!");
        } else {
            this.idade = idade;
        }
    }

    public void setPeso(double peso) {
        if (peso > 60 || peso < 0.5) {
            throw new PesoException("Peso inválido, por favor revise o valor informado!");
        } else {
            this.peso = peso;
        }
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
