package com.example.app;

import android.os.Environment;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class tf_idf {

    public static void calculateTFIDF() {
        //Path to the index directory
        String indexPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "MyAppIndex";

        try {
            //Create the index directory
            FSDirectory directory = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                directory = FSDirectory.open(Paths.get(indexPath));
            }

            //Create an analyzer for tokenization
            Analyzer analyzer = new StandardAnalyzer();

            //Create an index writer
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

            //Create a document with the content to be indexed
            Document document = new Document();
            //Add a TextField to the document
            FieldType fieldType = new FieldType();
            fieldType.setStored(true);
            Field contentField = new Field("content", "example document content", fieldType);
            document.add(contentField);

            //Add the document to the index
            indexWriter.addDocument(document);

            //Commit the changes and close the index writer
            indexWriter.commit();
            indexWriter.close();

            //Create a searcher to search the index
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            //Create a query to search for documents
            QueryParser queryParser = new QueryParser("content", analyzer);
            Query query = queryParser.parse("example");

            //Search for documents matching the query
            TopDocs topDocs = indexSearcher.search(query, 10);

            //Iterate over the search results and print the TF-IDF scores
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                int docId = scoreDoc.doc;
                float score = scoreDoc.score;
                System.out.println("Document ID: " + docId + ", TF-IDF Score: " + score);
            }

            //Close the index reader
            indexReader.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
