package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//NotificationBadge notificationBadge;
//ImageView img;
int count=1;
    LinearLayout ly1,ly2;
    DrawerLayout drawerLayout;
    NavigationView nv;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // notificationBadge=findViewById(R.id.badge);
        //img=findViewById(R.id.notify);
        nv=findViewById(R.id.nv);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        nv.bringToFront();
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setfrag(new homefragment());
        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationBadge.setNumber(count++);
            }
        });*/
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home)
                {
                    setfrag(new homefragment());
                }
                else if(id==R.id.donate){
                    startActivity(new Intent(MainActivity.this,application.class));
                }
                else if(id==R.id.aval){
                    startActivity(new Intent(MainActivity.this,bloodavailable.class));
                }
                else if(id==R.id.logout)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure you want to logout?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(new internet().isconnected(MainActivity.this)){
                                SharedPreferences sp=getSharedPreferences("first",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.remove("password");
                                editor.remove("name");
                                editor.remove("email");
                                editor.commit();
                                startActivity(new Intent(MainActivity.this,loginpage.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        nv.setCheckedItem(R.id.home);
       /* nv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.home)
                {
                    setfrag(new homefragment());
                }
                else if(id==R.id.profile)
                {
                   setfrag(new profilefragement());
                }
                return true;
            }
        });
        nv.setSelectedItemId(R.id.home);*/

    }
public void setfrag(Fragment fragment)
{
    FragmentManager fm=getSupportFragmentManager();
    FragmentTransaction ft=fm.beginTransaction();
    ft.add(R.id.frame,fragment);
    ft.commit();
}
}