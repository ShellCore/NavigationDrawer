package mx.shellcore.android.navigationdrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mx.shellcore.android.navigationdrawer.adapters.DrawerListAdapter;
import mx.shellcore.android.navigationdrawer.fragments.ArticleFragment;
import mx.shellcore.android.navigationdrawer.models.DrawerItem;

public class MainActivity extends AppCompatActivity {

    ArrayList<DrawerItem> items;
    private String[] tagTitles;
    private CharSequence activityTitle;
    private CharSequence itemTitle;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemTitle = activityTitle = getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        inicializaListaOpciones();

        inicializaEventosAperturaCierre();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void inicializaListaOpciones() {
        tagTitles = getResources().getStringArray(R.array.tags);
        items = new ArrayList<>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.ic_facebook));
        items.add(new DrawerItem(tagTitles[1], R.drawable.ic_skype));
        items.add(new DrawerItem(tagTitles[2], R.drawable.ic_twitter));
        items.add(new DrawerItem(tagTitles[3], R.drawable.ic_whatsapp));
        drawerList.setAdapter(new DrawerListAdapter(getApplicationContext(), items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void selectItem(int position) {
        Fragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_ARTICLES_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }


    private void inicializaEventosAperturaCierre() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(itemTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(activityTitle);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }


    class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
