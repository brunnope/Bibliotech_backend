package com.bibliotech.bibliotech.relatorio;

import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioEmprestimo {

    public static byte[] gerarPdfEmprestimos(List<EmprestimoDTO> emprestimos) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Relatório de Empréstimos")
                    .setBold()
                    .setFontSize(14)
                    .setMarginBottom(15)
                    .setTextAlignment(TextAlignment.CENTER));


            float[] columnWidths = {2, 3, 2, 3, 2, 2, 2, 3, 3, 2};
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));

            table.addCell(new Cell().add(new Paragraph("Nome").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Email").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Matrícula").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Livro").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Nº Exemplar").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Editora").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Data Empréstimo").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Data Prevista Devolução").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Data Devolução").setBold().setFontSize(10)));
            table.addCell(new Cell().add(new Paragraph("Status").setBold().setFontSize(10)));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (EmprestimoDTO emprestimo : emprestimos) {
                table.addCell(new Cell().add(new Paragraph(emprestimo.getUsuario().getNome()).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(emprestimo.getUsuario().getEmail()).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(emprestimo.getUsuario().getMatricula()).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(emprestimo.getExemplar().getLivro().getTitulo()).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(emprestimo.getExemplar().getIdExemplar())).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(emprestimo.getExemplar().getEditora().getNome()).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(
                        emprestimo.getDataEmprestimo() != null ? emprestimo.getDataEmprestimo().format(dateFormatter) : "").setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(
                        emprestimo.getDataPrevistaDevolucao() != null ? emprestimo.getDataPrevistaDevolucao().format(dateFormatter) : "").setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(
                        emprestimo.getDataDevolucao() != null ? emprestimo.getDataDevolucao().format(dateFormatter) : "").setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(emprestimo.getStatus().name()).setFontSize(9)));
            }

            document.add(table.setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Gerado em: " + LocalDate.now().format(dateFormatter)))
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}