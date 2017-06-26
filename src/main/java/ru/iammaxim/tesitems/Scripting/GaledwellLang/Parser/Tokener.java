package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 2/13/17 at 4:25 PM.
 */
public class Tokener {
    public ArrayList<Token> tokens;
    public int index = 0;
    public int currentLineNumber = 0;

    public Tokener(ArrayList<Token> tokens, int currentLineNumber) {
//        System.out.println("Creating Tokener with line: " + currentLineNumber);
        this.tokens = tokens;
        this.currentLineNumber = currentLineNumber;
    }

    public Tokener(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Tokener(Token... tokens) {
        this.tokens = new ArrayList<>();
        for (Token token : tokens) {
            this.tokens.add(token);
        }
    }

    public int left() {
        int _index = index;
        int left = tokens.size() - index;
        while (left > 0) {
            if (tokens.get(_index).type != TokenType.NEW_LINE)
                return left;
            _index++;
            left--;
        }
        return 0;
    }

    public Token eat() {
        Token t = tokens.get(index++);
        while (t.type == TokenType.NEW_LINE) {
            currentLineNumber++;
//            System.out.println("increased line number. Now: " + currentLineNumber);
            if (left() == 0) {
                t = null;
                break;
            }
            t = tokens.get(index++);
        }
//        System.out.println("ate " + t);
        if (t instanceof TokenScope) {
            ((TokenScope) t).tokens.forEach(token -> {
                if (token.type == TokenType.NEW_LINE)
                    currentLineNumber++;
            });
        }
        return t;
    }

    public Token peekNext() {
        int _index = index;
        Token t = tokens.get(_index);
        while (t.type == TokenType.NEW_LINE) {
            if (tokens.size() - _index - 1 == 0)
                return null;
            t = get(++_index);
        }
        return t;
    }

    public Token peekNextNext() {
        int _index = index + 1;
        Token t = tokens.get(_index);
        while (t.type == TokenType.NEW_LINE) {
            if (tokens.size() - _index - 1 == 0)
                return null;
            t = get(++_index);
        }
        return t;
    }

    public Token peekPrev() {
        int _index = index - 2;
        Token t = tokens.get(_index);
        while (t.type == TokenType.NEW_LINE) {
            if (_index == 0)
                return null;
            t = get(--_index);
        }
        return t;
    }

    public Token eatWithLineNumbers() {
        Token t = tokens.get(index++);
        if (t.type == TokenType.NEW_LINE)
            currentLineNumber++;
        return t;
    }

    public Tokener readTo(Token token) throws InvalidTokenException {
        ArrayList<Token> tokens = new ArrayList<>();
        int lineNumber = currentLineNumber;
        while (left() > 0) {
            Token t = eatWithLineNumbers();
            if (t.equals(token))
                break;

            tokens.add(t);
        }
        if (index == tokens.size())
            throw new InvalidTokenException("Line " + currentLineNumber + ": Expected " + token.token);
        return new Tokener(tokens, lineNumber);
    }

    public Tokener readToSkippingScopes(Token token) throws InvalidTokenException {
        ArrayList<Token> tokens = new ArrayList<>();
        int lineNumber = currentLineNumber;
        int level = 0; //because first brace/paren has already been read
        while (left() > 0) {
            Token t = eatWithLineNumbers();

            if (t.type == TokenType.BRACE_OPEN || t.type == TokenType.PAREN_OPEN)
                level++;
            if (t.type == TokenType.BRACE_CLOSE || t.type == TokenType.PAREN_CLOSE)
                level--;

            if (level > 0) {
                tokens.add(t);
                continue;
            }

            if (t.equals(token))
                break;

            tokens.add(t);
        }
        if (level != 0)
            throw new InvalidTokenException("Line " + currentLineNumber + ": Expected " + token.token);

        tokens.remove(0); //remove first scope
        return new Tokener(tokens, lineNumber);
    }

    @Override
    public String toString() {
        return tokens.toString();
    }

    public boolean isEmpty() {
        return tokens.isEmpty();
    }

/*    public ArrayList<Tokener> splitByFirst(Token token) {
        ArrayList<Tokener> parts = new ArrayList<>();
        ArrayList<Token> tokens = new ArrayList<>();
        index = 0;
        while (left() > 0) {
            Token t = eat();
            if (t.equals(token)) {
                parts.add(new Tokener(tokens));
                tokens = new ArrayList<>();
                while (left() > 0) {
                    tokens.add(eat());
                }
                break;
            } else
                tokens.add(t);
        }
        //add last part that is not followed by delimiter
        parts.add(new Tokener(tokens));
        return parts;
    }*/

    //including begin, excluding end
    public Tokener subtokener(int begin, int end) {
        ArrayList<Token> subList = new ArrayList<>(end - begin);
        int lineNumber = 1;
        for (int i = 0; i < begin; i++)
            if (tokens.get(i).type == TokenType.NEW_LINE)
                lineNumber++;
        for (int i = begin; i < end; i++)
            subList.add(tokens.get(i));
        return new Tokener(subList, lineNumber);
    }

    public int size() {
        return tokens.size();
    }

    public void trimParentheses() throws InvalidTokenException {
        if (size() >= 2)
            while (tokens.get(0).equals(new Token("(")) && tokens.get(tokens.size() - 1).equals(new Token(")"))) {
                tokens.remove(0);
                tokens.remove(tokens.size() - 1);
            }
    }

    public Token get() {
        return tokens.get(index);
    }

    public Token get(int i) {
        return tokens.get(i);
    }

    public ArrayList<Tokener> splitSkippingScopes(Token token) {
        ArrayList<Tokener> parts = new ArrayList<>();
        ArrayList<Token> tokens = new ArrayList<>();
        index = 0;
        int level = 0;
        int lineNumber = currentLineNumber;
        System.out.println("running splitSkippingScopes on: " + this);
        System.out.println("lineNumber: " + lineNumber);
        while (left() > 0) {
            if (tokens.isEmpty()) {
                lineNumber = currentLineNumber;
                System.out.println("new lineNumber: " + lineNumber);
            }
            Token t = eat();
            if (t == null)
                break;

            if (t.type == TokenType.BRACE_OPEN || t.type == TokenType.PAREN_OPEN)
                level++;
            if (t.type == TokenType.BRACE_CLOSE || t.type == TokenType.PAREN_CLOSE)
                level--;

            if (level > 0) {
                tokens.add(t);
                continue;
            }

            if (t.equals(token)) {
                if (tokens.size() > 0) {
                    parts.add(new Tokener(tokens, lineNumber));
                    System.out.println("adding part " + parts.get(parts.size() - 1));
                    tokens = new ArrayList<>();
                }
            } else {
                tokens.add(t);
            }
        }
        //add last part that is not followed by delimiter
        if (tokens.size() > 0) {
            parts.add(new Tokener(tokens, lineNumber));
            System.out.println("adding part " + parts.get(parts.size() - 1));
        }
        return parts;
    }

    public void remove(int index) {
        tokens.remove(index);
    }

    public void add(int index, Token token) {
        tokens.add(index, token);
    }
}
