package net.todd.scorekeeper;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ManagePlayersActivityTest {
    private ManagePlayersActivity testObject;
    private Button addPlayerButton;

    @Before
    public void setup() {
        ActivityController<ManagePlayersActivity> controller = Robolectric.buildActivity(ManagePlayersActivity.class);
        testObject = controller.create().resume().get();
        addPlayerButton = (Button)testObject.findViewById(R.id.add_player_button);
    }

    @Test
    public void initiallyAddPlayerButtonIsDisabled() {
        assertThat(addPlayerButton.isEnabled(), is(false));
    }
}
