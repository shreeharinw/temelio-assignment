import React, { useState } from 'react';
import { Dialog } from 'primereact/dialog';
import { TabView, TabPanel } from 'primereact/tabview';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import 'primereact/resources/themes/saga-blue/theme.css'; // Theme CSS
import 'primereact/resources/primereact.min.css'; // Core CSS

const AddData = ({ visible, onHide }) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const [foundationData, setFoundationData] = useState({
    email: ''
  });
  const [nonProfitData, setNonProfitData] = useState({
    name: '',
    address: '',
    email: ''
  });

  const handleSave = () => {
    if (activeIndex === 0) {
      // Save Foundation data
      axios.post('http://localhost:8080/api/foundations', foundationData)
        .then(response => {
          console.log('Foundation saved successfully:', response.data);
          onHide();
        })
        .catch(error => {
          console.error('Error saving Foundation:', error);
        });
    } else if (activeIndex === 1) {
      // Save Non-Profit data
      axios.post('http://localhost:8080/api/nonprofits', nonProfitData)
        .then(response => {
          console.log('Non-Profit saved successfully:', response.data);
          onHide();
        })
        .catch(error => {
          console.error('Error saving Non-Profit:', error);
        });
    }
  };

  return (
    <Dialog visible={visible} onHide={onHide} header="Add Data" modal style={{ width: '30vw' }} position="center">
      <div className="p-fluid">
        <TabView activeIndex={activeIndex} onTabChange={(e) => setActiveIndex(e.index)}>
          <TabPanel header="Add Foundation">
            <div className="p-grid p-fluid">
              <div className="p-col">
                <div className="p-field">
                  <label htmlFor="email">Email</label>
                  <InputText id="email" type="email" value={foundationData.email} onChange={(e) => setFoundationData({ ...foundationData, email: e.target.value })} />
                </div>
              </div>
            </div>
          </TabPanel>
          <TabPanel header="Add Non-Profit">
            <div className="p-grid p-fluid">
              <div className="p-col">
                <div className="p-field">
                  <label htmlFor="name">Name</label>
                  <InputText id="name" type="text" value={nonProfitData.name} onChange={(e) => setNonProfitData({ ...nonProfitData, name: e.target.value })} />
                </div>
                <div className="p-field">
                  <label htmlFor="address">Address</label>
                  <InputText id="address" type="text" value={nonProfitData.address} onChange={(e) => setNonProfitData({ ...nonProfitData, address: e.target.value })} />
                </div>
                <div className="p-field">
                  <label htmlFor="nonProfitEmail">Email</label>
                  <InputText id="nonProfitEmail" type="email" value={nonProfitData.email} onChange={(e) => setNonProfitData({ ...nonProfitData, email: e.target.value })} />
                </div>
              </div>
            </div>
          </TabPanel>
        </TabView>
        <div className="p-d-flex p-jc-end" style={{ marginTop: '1rem' }}>
          <Button label="Cancel" className="p-button-text" onClick={onHide} style={{ marginRight: '1rem' }} />
          <Button label="Save" className="p-button-primary" onClick={handleSave} style={{ minWidth: '6rem' }} />
        </div>
      </div>
    </Dialog>
  );
};

export default AddData;
