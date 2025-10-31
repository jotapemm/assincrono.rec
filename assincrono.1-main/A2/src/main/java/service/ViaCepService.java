package service;

import com.google.gson.Gson;
import model.Endereco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Serviço para consultar endereços via API ViaCEP
 * API: https://viacep.com.br/
 */
public class ViaCepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";
    private final Gson gson;

    public ViaCepService() {
        this.gson = new Gson();
    }

    /**
     * Consulta um endereço pelo CEP na API ViaCEP
     *
     * @param cep CEP a ser consultado (pode conter hífen ou não)
     * @return Endereco com os dados preenchidos ou null se houver erro
     */
    public Endereco consultarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            System.out.println("CEP invalido!");
            return null;
        }

        // Remove caracteres não numéricos do CEP
        String cepLimpo = cep.replaceAll("[^0-9]", "");

        if (cepLimpo.length() != 8) {
            System.out.println("CEP deve conter 8 digitos!");
            return null;
        }

        try {
            String urlString = String.format(VIA_CEP_URL, cepLimpo);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8")
                );
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Converte JSON para objeto
                ViaCepResponse viaCepResponse = gson.fromJson(response.toString(), ViaCepResponse.class);

                // Verifica se houve erro na resposta
                if (viaCepResponse.isErro()) {
                    System.out.println("CEP nao encontrado!");
                    return null;
                }

                // Converte ViaCepResponse para Endereco
                return converterParaEndereco(viaCepResponse);

            } else {
                System.out.println("Erro ao consultar CEP. Codigo de resposta: " + responseCode);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar API ViaCEP: " + e.getMessage());
            return null;
        }
    }

    /**
     * Converte a resposta da API ViaCEP para um objeto Endereco
     */
    private Endereco converterParaEndereco(ViaCepResponse response) {
        Endereco endereco = new Endereco();
        endereco.setEstado(response.getUf() != null ? response.getUf() : "");
        endereco.setCidade(response.getLocalidade() != null ? response.getLocalidade() : "");
        endereco.setRua(response.getLogradouro() != null ? response.getLogradouro() : "");
        endereco.setCep(response.getCep() != null ? response.getCep() : "");
        // Número será preenchido depois pelo usuário
        endereco.setNumero("");
        return endereco;
    }
}