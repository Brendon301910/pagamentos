package com.projeto.pagamentos.utils;

import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

public class UrlCleaner {

    public static String cleanUrl(String url) {
        try {
            // Decodifica a URL (para garantir que caracteres especiais sejam removidos)
            String decodedUrl = URLDecoder.decode(url, "UTF-8");
            // Remove quebras de linha e espaços extras
            decodedUrl = decodedUrl.replaceAll("[\\n\\r\\t]", "").trim();
            return decodedUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
    }

    public static void main(String[] args) {
        String url = "http://localhost:8080/pagamentos/status%0A";
        String cleanedUrl = cleanUrl(url);
        System.out.println(cleanedUrl); // Deveria remover o %0A e retornar a URL correta
    }
}
