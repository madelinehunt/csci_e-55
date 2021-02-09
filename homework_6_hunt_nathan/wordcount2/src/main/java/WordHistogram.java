package cscie55.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordHistogram {

    public static class MyMapper
            extends Mapper<Object, Text, IntWritable, IntWritable>{

        // private Text word = new Text();
        private IntWritable length = new IntWritable();
        private final static IntWritable one = new IntWritable(1);
    
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String str = itr.nextToken();
                str = str.toLowerCase().replaceAll("[^a-zA-Z]","").replaceAll("\\s", "");
                // word.set(str);
                if (!str.isEmpty()) {
                    length.set(str.length());
                    System.out.println(length);
                    context.write(length, one);
                }
            }
        }
    }

    public static class MyReducer
            extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(IntWritable key, Iterable<IntWritable> values,
                           Context context
                           ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        // these vars are tmp for dev purposes
        // comment these 3 lines before putting into a jar for running in AWS
        String FILENAME = args[0];
        //System.out.println( WordHistogram.class.getClassLoader().getResource( FILENAME ) );
        String input = WordHistogram.class.getClassLoader().getResource(FILENAME).getFile();//("").getPath().replaceFirst("^/(.:/)", "$1");
        String output = new String("target/")+args[1]; // replace real cmd line args w/ hard-coded

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordHistogram.class);
        job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        // these lines are for dev purposes
        // comment these 2 lines before putting into a for running in AWS
        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));


        // UNCOMMENT these 2 lines before putting into a jar for running in AWS

        //FileInputFormat.addInputPath(job, new Path(args[0]));
        //FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}


