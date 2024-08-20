package com.example.confluence_api.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

public class ConfluenceContentBodyProcessor 
{

    public static int countLines(String html)
    {
        return extractLines(html).size();
    }

    public static List<String> extractLines(String html) 
    {
        // Decode Unicode sequences
        String decodedHtml = decodeUnicodeSequences(html);

        // Parse the decoded HTML string using Jsoup
        Document document = Jsoup.parse(decodedHtml);

        // This list will hold the lines of text
        List<String> lines = new ArrayList<>();

        // StringBuilder to accumulate text for the current line
        StringBuilder currentLine = new StringBuilder();

        // Recursively process the document starting from the body element
        processNode(document.body(), lines, currentLine);

        // Add the last line if there is any remaining text
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }

    // Method to decode Unicode sequences
    public static String decodeUnicodeSequences(String input) 
    {
        return input.replace("\\u003C", "<")
                    .replace("\\u003E", ">")
                    .replace("\\u0022", "\"")
                    .replace("\\u0027", "'")
                    .replace("\\u002F", "/");
    }

    private static void processNode(Node node, List<String> lines, StringBuilder currentLine) 
    {
        // Handle text nodes directly by adding their text to the current line
        if (node instanceof TextNode) 
        {
            TextNode textNode = (TextNode) node;
            currentLine.append(textNode.text());
        } 
        else if (node instanceof Element) 
        {
            Element element = (Element) node;

            // Check tag behavior in one place
            boolean isBlockElement = false;
            switch (element.tagName()) 
            {
                case "p":
                case "div":
                case "h1":
                case "h2":
                case "h3":
                case "h4":
                case "h5":
                case "h6":
                case "li":
                    isBlockElement = true;
                    break;
                case "br":
                    // Line break: finalize the current line
                    lines.add(currentLine.toString().trim());
                    currentLine.setLength(0); // Reset the current line
                    break;
                default:
                    break;
            }

            // switch (element.tagName()) 
            // {
            //     case "p":
            //     case "div":
            //     case "h1":
            //     case "h2":
            //     case "h3":
            //     case "h4":
            //     case "h5":
            //     case "h6":
            //     case "li":
            //     case "ul":
            //     case "ol":
            //     case "table":
            //     case "thead":
            //     case "tbody":
            //     case "tfoot":
            //     case "tr":
            //     case "th":
            //     case "td":
            //     case "blockquote":
            //     case "section":
            //     case "article":
            //     case "aside":
            //     case "header":
            //     case "footer":
            //     case "nav":
            //     case "figure":
            //     case "figcaption":
            //     case "pre":
            //     case "hr":
            //         isBlockElement = true;
            //         break;
            //     case "br":
            //         // Line break: finalize the current line
            //         lines.add(currentLine.toString().trim());
            //         currentLine.setLength(0); // Reset the current line
            //         break;
            //     default:
            //         break;
            // }

            // Recursively process child nodes
            for (Node child : element.childNodes()) 
            {
                processNode(child, lines, currentLine);
            }

            // If the element is a block-level element, finalize the line after processing children
            if (isBlockElement && currentLine.length() > 0) 
            {
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0); // Reset the current line
            }
        }
    }
}
