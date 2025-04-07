# Advanced Technology Lab

## NOTE
If running in the lab, then go to **Grade Scripts** -> `libs.versions.toml` and change **AGP** under `[versions]` to `8.7.3` from `8.8.0` or greater.

<div style="display: flex; justify-content: space-around; gap: 10px; align-items: center;">
    <figure style="text-align: center; height: 700px;">
        <img src="assets/toml_change.png" alt="TOML change" style="width: 100%; height: 100%; object-fit: cover;">
    </figure>
    
</div>


## Steps to Open SQLite DB File in Android Studio

<div style="display: flex; justify-content: space-around; gap: 10px; align-items: center;">
    <figure style="text-align: center; width: 30%; height: 600px;">
        <img src="assets/Step1.png" alt="Step 1" style="width: 100%; height: 100%; object-fit: cover; object-position: -60px 50%;">
        <figcaption>Go to <strong>View</strong> -> <strong>Tool Windows</strong> -> <strong>Device Explorer</strong></figcaption>
    </figure>
    <figure style="text-align: center; width: 30%; height: 600px;">
        <img src="assets/Step2.png" alt="Step 2" style="width: 100%; height: 100%; object-fit: cover;">
        <figcaption>Navigate to <strong>/data</strong> -> <strong>data</strong> -> <code>com.example.project_name</code> -> <strong>databases</strong></figcaption>
    </figure>
    <figure style="text-align: center; width: 30%; height: 600px;">
        <img src="assets/Step3.png" alt="Step 3" style="width: 100%; height: 100%; object-fit: cover;">
        <figcaption>Either open the <code>.db</code> file with default settings OR save it into a known directory and open using <strong>SQLite Browser</strong></figcaption>
    </figure>
</div>
