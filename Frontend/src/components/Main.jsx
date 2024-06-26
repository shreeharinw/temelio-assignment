import React, { useState, useEffect, useCallback } from 'react';
import { TabView, TabPanel } from 'primereact/tabview';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS
import axios from 'axios';
import AddData from './AddData';
import { Button } from 'primereact/button';
import NonProfits from './NonProfits';
import EmailData from './EmailData';
import './Main.css'; // Import your custom CSS file
import './GrantData';
// import GrantData from './GrantData';
import GrantData from './GrantData';
const Main = () => {
  const [foundations, setFoundations] = useState([]);
  const [nonProfits, setNonProfits] = useState([]);
  const [grants, setGrants] = useState([]);
  const [displayAddData, setDisplayAddData] = useState(false);

  const fetchData = useCallback(() => {
    axios.get('http://localhost:8080/api/foundations')
      .then(response => {
        setFoundations(response.data);
      })
      .catch(error => {
        console.error('Error fetching foundations:', error);
      });

    axios.get('http://localhost:8080/api/nonprofits')
      .then(response => {
        setNonProfits(response.data);
      })
      .catch(error => {
        console.error('Error fetching non-profits:', error);
      });
  }, []);

  // Function to fetch grant submissions
  const fetchGrantSubmissions = useCallback(() => {
    axios.get('http://localhost:8080/api/grantsubmissions')
      .then(response => {
        let grants = response.data;
        let editGrants = grants.map(grant=>{
          return {
            ...grant,
            tags:grant.tags.join(",")
          }
        })
        setGrants(grants);
      })
      .catch(error => {
        console.error('Error fetching grant submissions:', error);
      });
  }, []);

  useEffect(() => {
    // Fetch foundations and non-profits data initially
    fetchData();

    // Fetch grant submissions
    fetchGrantSubmissions();
  }, [fetchData, fetchGrantSubmissions, displayAddData]);

  // Function to handle successful upload and fetch grants again
  const handleSuccessfulUpload = () => {
    fetchGrantSubmissions(); // Fetch grants again after successful upload
  };

  return (
    <div className="container">
      <div className="button-container p-mb-3">
        <Button label="Add Foundation/Non-Profit" className="p-button-primary" onClick={() => setDisplayAddData(true)} />
      </div>
      <TabView>
        <TabPanel header="Foundations">
          <DataTable value={foundations} className="p-datatable-striped">
            <Column field="id" header="ID" />
            <Column field="email" header="Email" />
            {/* Add more columns as needed */}
          </DataTable>
        </TabPanel>
        <TabPanel header="Non-Profits">
          <NonProfits foundations={foundations} nonProfits={nonProfits}/>
        </TabPanel>
        <TabPanel header="Emails">
          <EmailData foundations={foundations}/>
        </TabPanel>
        <TabPanel header="Grants">
          <GrantData data={grants} onUploadSuccess={handleSuccessfulUpload}/>
        </TabPanel>
      </TabView>
      {/* AddData modal */}
      <AddData visible={displayAddData} onHide={() => setDisplayAddData(false)} />
    </div>
  );
};

export default Main;
