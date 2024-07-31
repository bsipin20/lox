package org.bsipin.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private final List<Token> tokens = new ArrayList<>();

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR); break;
            case '!': addToken(match('=') ? TokenType.BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? TokenType.EQUAL_EQUAL: EQUAL); break;
            case '<': addToken(match('=') ? TokenType.LESS_EQUAL: LESS); break;
            case '>': addToken(match('=') ? TokenType.GREATER_EQUAL: GREATER); break;
            case '/':
                    if(match('/')) {
                        while(peek() != '\n' && !isAtEnd()) advance();
                    } else {
                        addToken(SLASH);
                    }
                    break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
            case '\n':
                line++;
                break;
            default:
                    Lox.error(line, "Unexpected Character.");
                    break;
        }
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType token) {
        addToken(token, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
