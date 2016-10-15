<?php
set_time_limit(60);
// $argc =4;
// $argv = ["word2pdf.php","inputFileName1.docx", "outputFileName1.pdf", "F:\Tools\Program Handle\WAMP\www\lec\lectureDealServer\public/upload/lecture/"];
if($argc != 4){
   exit(1);
}else{
   $inputFileName = $argv[1];
   $outputFileName = $argv[2];
   $base_dir = $argv[3];
   try{
      $doc_file = $base_dir.$inputFileName;
      $pdf_file = $base_dir.$outputFileName; 
 
      $word = new COM("word.application") or die("Unable to instanciate Word"); 
      // set it to 1 to see the MS Word window (the actual opening of the document)
      $word->Visible = 0;
      // recommend to set to 0, disables alerts like "Do you want MS Word to be the default .. etc"
      $word->DisplayAlerts = 0;
      // open the word 2007-2013 document 
      $word->Documents->Open($doc_file);
      // convert word 2007-2013 to PDF
      $word->ActiveDocument->ExportAsFixedFormat($pdf_file, 17, false, 0, 0, 0, 0, 7, true, true, 2, true, true, false);
      // quit the Word process
      $word->Quit(false);
      // clean up
      unset($word);
      exit(0);
   }catch (Exception $e) {
       echo 'Caught exception: ',  $e->getMessage(), "\n";
       exit(1);
   }
}
?>