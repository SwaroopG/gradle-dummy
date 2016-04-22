package com.poorjar.test;

import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class RegexTest
{
    private static final Logger LOGGER = LogManager.getLogger(RegexTest.class);
    private static Pattern PATTERN = Pattern.compile("\\bHD\\b", Pattern.MULTILINE);

    @Test
    public void testRegex()
    {
        // "Maze Runner: The Scorch Trials HD"
        // "Maze Runner: The Scorch Trials (HD)"

        // "HD Version. Season 2. Nancy grows her business; Andy takes Shane to a massage parlor."
        // "HD. 'What Happened To You?' (Season Two) Coincidence brings Carlo to a 22-year-old woman dying in the hospital. Ends 04/09"
        // "(HD) In "The
        // Intern," Ben Whittaker is a 70-year-old widower who has discovered that retirement isn't all it's cracked up to be. Stars Robert DeNiro & Anne Hathaway. (CC)"
        // "LCK Ep 1: Switch Hitters," 12/04/2015: The first two Eliminated
        // Chefs will have to make winning dishes out of losing ingredients. HD
        // Version"

        String title = "Transporter Refueled (HD)";
        String description = "(HD). HD. HD that tested test is HD testihD BD tester's hdtestshd HD";
        Assert.assertTrue(isHD(title, description));

        title = "Transporter Refueled";
        description = "(that tested test is testihD BD tester's hdtestshHD";
        Assert.assertFalse(isHD(title, description));

        title = "Transporter Refueled";
        description = "(that tested test is testihD BD tester's hdtestshHD";
        Assert.assertFalse(isHD(title, description));

        title = null;
        description = "HD Version. Season 2. Nancy grows her business; Andy takes Shane to a massage parlor.";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "HD. 'What Happened To You?' (Season Two) Coincidence brings Carlo to a 22-year-old woman dying in the hospital. Ends 04/09";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "(HD) In \"The Intern,\" Ben Whittaker is a 70-year-old widower who has discovered that retirement isn't all it's cracked up to be. Stars Robert DeNiro ";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "LCK Ep 1: Switch Hitters,\" 12/04/2015: The first two Eliminated Chefs will have to make winning dishes out of losing ingredients. HD ";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "HD Adam Jones (Bradley Cooper) is a Chef who destroyed his career with drugs and diva...";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "Doug and Abi and their three children travel to the Scottish Highlands for Doug's father birthday party...";
        Assert.assertFalse(isHD(title, description));

        title = null;
        description = "2006. HD Version. With only three hundred soldiers behind him, the King of Sparta leads his army...";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "(2008) HD.Won: Best Supporting Actor. Batman, Gordon and Harvey Dent are forced to deal with...";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "TOMATOMETER (TM): 95%.HD. Directed by Steven Spielberg. Indy and his feisty ex-flame Marion...";
        Assert.assertTrue(isHD(title, description));

        title = null;
        description = "HD Version: A trip to St. Pierre; Fortune Head Eco Reserve; Entrepreneur Brian Rose; The Provincial Seaman's Museum.";
        Assert.assertTrue(isHD(title, description));
    }

    public static boolean isHD(String title, String description)
    {
        // Check if TITLE has the word HD. If title does not have HD Pattern then check DESCRIPTION field.
        if (title != null && !title.isEmpty() && PATTERN.matcher(title).find())
        {
            LOGGER.log(Level.DEBUG, "title true");
            return true;
        }

        // Check if Description has the word HD.
        if (description != null && !description.isEmpty() && PATTERN.matcher(description).find())
        {
            return true;
        }
        return false;
    }
}
