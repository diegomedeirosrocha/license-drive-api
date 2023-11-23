package br.com.diego.licensedriveapi.enums;
/**
 * Enum com todas as Unidades da Federação do Brasil. Contém o nome da Unidade, a sigla e a capital
 * da Unidade da Federação. As unidades estão listadas por ordem alfabética no enum.
 *
 * @author Ricardo Giaviti
 * @version 1.1.1
 * @since 1.0.0
 */
//reference class OEM in link: https://gist.github.com/rgiaviti/510c260164ea25ef0449209f26560c3d
public enum UF {

    AMAZONAS("Amazonas", "AM", "Manaus"),
    ALAGOAS("Alagoas", "AL", "Maceió"),
    ACRE("Acre", "AC", "Rio Branco"),
    AMAPA("Amapá", "AP", "Macapá"),
    BAHIA("Bahia", "BA", "Salvador"),
    PARA("Pará", "PA", "Belém"),
    MATO_GROSSO("Mato Grosso", "MT", "Cuiabá"),
    MINAS_GERAIS("Minas Gerais", "MG", "Belo Horizonte"),
    MATO_GROSSO_DO_SUL("Mato Grosso do Sul", "MS", "Campo Grande"),
    GOIAS("Goiás", "GO", "Goiânia"),
    MARANHAO("Maranhão", "MA", "São Luís"),
    RIO_GRANDE_DO_SUL("Rio Grande do Sul", "RS", "Porto Alegre"),
    TOCANTINS("Tocantins", "TO", "Palmas"),
    PIAUI("Piauí", "PI", "Teresina"),
    SAO_PAULO("São Paulo", "SP", "São Paulo"),
    RONDONIA("Rondônia", "RO", "Porto Velho"),
    RORAIMA("Roraima", "RR", "Boa Vista"),
    PARANA("Paraná", "PR", "Curitiba"),
    CEARA("Ceará", "CE", "Fortaleza"),
    PERNAMBUCO("Pernambuco", "PE", "Recife"),
    SANTA_CATARINA("Santa Catarina", "SC", "Florianópolis"),
    PARAIBA("Paraíba", "PB", "João Pessoa"),
    RIO_GRANDE_DO_NORTE("Rio Grande do Norte", "RN", "Natal"),
    ESPIRITO_SANTO("Espírito Santo", "ES", "Vitória"),
    RIO_DE_JANEIRO("Rio de Janeiro", "RJ", "Rio de Janeiro"),
    SERGIPE("Sergipe", "SE", "Aracaju"),
    DISTRITO_FEDERAL("Distrito Federal", "DF", "Brasília");

    private final String nome;
    private final String sigla;
    private final String capital;

    /**
     * Construtor do enum
     *
     * @param nome    nome da unidade da federação completo
     * @param sigla   sigla da unidade da federação
     * @param capital nome da capital da unidade da federação
     */
    UF( final String nome, final String sigla, final String capital) {
        this.nome = nome;
        this.sigla = sigla;
        this.capital = capital;
    }

    /**
     * Converte a partir da Sigla da UF no parâmetro, para o enum da Unidade da Federação.
     *
     * @param sigla da Unidade da Federação. Exemplo: "MG"
     * @return a Unidade da Federação
     * @throws IllegalArgumentException caso a sigla da UF não exista
     */
    public static UF fromSigla( final String sigla) {
        for (final UF uf : UF.values()) {
            if (uf.sigla.equalsIgnoreCase(sigla)) {
                return uf;
            }
        }
        throw new IllegalArgumentException(sigla + " is invalid");
    }

    /**
     * Obtém a sigla da UF
     *
     * @return a sigla da UF
     */
    public String sigla() {
        return this.sigla;
    }
}
