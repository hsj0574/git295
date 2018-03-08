package cn.itcast.lucene.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.itcast.lucene.dao.BookDao;
import cn.itcast.lucene.dao.BookDaoImpl;
import cn.itcast.lucene.pojo.Book;

public class booktest {
	
	
	@Test
	public void testName() throws Exception {
		//获取文档  
				BookDao bookDao = new BookDaoImpl();
				List<Book> books = bookDao.queryBookList();
				//分词  分析文档
		Analyzer analyzer = new StandardAnalyzer();
			//	Analyzer analyzer = new IKAnalyzer();
				//索引的位置
				Directory directory = FSDirectory.open(new File("D:\\index"));
				//创建索引
				IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
				IndexWriter indexWriter = new IndexWriter(directory, config);
				
				for (Book book : books) {
					//创建文档对象
					Document doc = new Document();
					//创建域对象
					//ID
					Field idField = new TextField("id", String.valueOf(book.getId()), Store.YES);
					//图书名称
					Field nameField = new TextField("name", book.getName(), Store.YES);
					//图书价格
					Field priceField = new TextField("price", String.valueOf(book.getPrice()), Store.YES);
					//图书图片
					Field picField = new TextField("pic", book.getPic(), Store.YES);
					//图书描述
					Field descField = new TextField("desc", book.getDesc(), Store.NO);
					doc.add(idField);
					doc.add(nameField);
					doc.add(priceField);
					doc.add(picField);
					doc.add(descField);
					//保存索引及文档到索引库中
					indexWriter.addDocument(doc);
				}
				//关闭资源 
				indexWriter.close();

	}

}
